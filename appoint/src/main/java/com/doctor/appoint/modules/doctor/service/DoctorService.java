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

    public List<DoctorResponseDTO> getDoctorsBySpecialty(Long specialtyId) {

        List<Long> doctorIds = new ArrayList<>();

        List<com.doctor.appoint.modules.doctor.entity.DoctorSpecialty> mappings =
                doctorSpecialtyRepository.findBySpecialtyId(specialtyId);

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

    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {

        Doctor doctor = new Doctor();
        doctor.setUserId(requestDTO.getUserId());
        doctor.setExperienceYears(requestDTO.getExperienceYears());
        doctor.setConsultationFeeOnline(requestDTO.getConsultationFeeOnline());
        doctor.setConsultationFeeOffline(requestDTO.getConsultationFeeOffline());
        doctor.setCreatedAt(LocalDateTime.now());

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToResponse(savedDoctor);
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

        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (doctorOptional.isPresent()) {
            return mapToResponse(doctorOptional.get());
        } else {
            throw new RuntimeException("Doctor not found");
        }
    }

    public DoctorResponseDTO getDoctorByUserId(Long userId) {

        Doctor doctor = doctorRepository.findByUserId(userId);

        if (doctor != null) {
            return mapToResponse(doctor);
        } else {
            throw new RuntimeException("Doctor not found with userId");
        }
    }

    private DoctorResponseDTO mapToResponse(Doctor doctor) {

        DoctorResponseDTO responseDTO = new DoctorResponseDTO();
        responseDTO.setDoctorId(doctor.getDoctorId());
        responseDTO.setUserId(doctor.getUserId());
        responseDTO.setExperienceYears(doctor.getExperienceYears());
        responseDTO.setConsultationFeeOnline(doctor.getConsultationFeeOnline());
        responseDTO.setConsultationFeeOffline(doctor.getConsultationFeeOffline());
        responseDTO.setCreatedAt(doctor.getCreatedAt());

        return responseDTO;
    }
}