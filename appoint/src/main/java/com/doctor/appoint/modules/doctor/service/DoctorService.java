package com.doctor.appoint.modules.doctor.service;

import com.doctor.appoint.modules.doctor.dto.request.DoctorRequestDTO;
import com.doctor.appoint.modules.doctor.dto.response.DoctorResponseDTO;
import com.doctor.appoint.modules.doctor.entity.Doctor;
import com.doctor.appoint.modules.doctor.repository.DoctorRepository;
import com.doctor.appoint.modules.doctor.repository.DoctorSpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorSpecialtyRepository doctorSpecialtyRepository;

    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {
        // Check if doctor profile already exists for this user
        Optional<Doctor> existing = doctorRepository.findByUserId(requestDTO.getUserId());
        if (existing.isPresent()) {
            throw new RuntimeException(
                    "Doctor profile already exists for userId: " + requestDTO.getUserId());
        }

        Doctor doctor = new Doctor();
        doctor.setUserId(requestDTO.getUserId());
        doctor.setExperienceYears(requestDTO.getExperienceYears());
        doctor.setConsultationFeeOnline(requestDTO.getConsultationFeeOnline());
        doctor.setConsultationFeeOffline(requestDTO.getConsultationFeeOffline());
        doctor.setCreatedAt(LocalDateTime.now());

        return mapToResponse(doctorRepository.save(doctor));
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorResponseDTO> responseList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            responseList.add(mapToResponse(doctor));
        }
        return responseList;
    }

    public DoctorResponseDTO getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException(
                        "Doctor not found with doctorId: " + doctorId));
        return mapToResponse(doctor);
    }

    public DoctorResponseDTO getDoctorByUserId(Long userId) {
        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(
                        "Doctor not found with userId: " + userId));
        return mapToResponse(doctor);
    }

    public List<DoctorResponseDTO> getDoctorsBySpecialty(Long specialtyId) {
        List<Long> doctorIds = new ArrayList<>();

        List<com.doctor.appoint.modules.doctor.entity.DoctorSpecialty> mappings =
                doctorSpecialtyRepository.findBySpecialtyId(specialtyId);

        if (mappings.isEmpty()) {
            throw new RuntimeException(
                    "No doctors found for specialtyId: " + specialtyId);
        }

        for (com.doctor.appoint.modules.doctor.entity.DoctorSpecialty mapping : mappings) {
            doctorIds.add(mapping.getDoctorId());
        }

        List<Doctor> doctors = doctorRepository.findAllById(doctorIds);
        List<DoctorResponseDTO> responseList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            responseList.add(mapToResponse(doctor));
        }
        return responseList;
    }

    private DoctorResponseDTO mapToResponse(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setDoctorId(doctor.getDoctorId());
        dto.setUserId(doctor.getUserId());
        dto.setExperienceYears(doctor.getExperienceYears());
        dto.setConsultationFeeOnline(doctor.getConsultationFeeOnline());
        dto.setConsultationFeeOffline(doctor.getConsultationFeeOffline());
        dto.setCreatedAt(doctor.getCreatedAt());
        return dto;
    }
}