package com.doctor.appoint.modules.doctor.service;

import com.doctor.appoint.modules.doctor.dto.response.DoctorSpecialtyResponse;
import com.doctor.appoint.modules.doctor.entity.DoctorSpecialty;
import com.doctor.appoint.modules.doctor.entity.Specialty;
import com.doctor.appoint.modules.doctor.repository.DoctorSpecialtyRepository;
import com.doctor.appoint.modules.doctor.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorSpecialtyService {

    @Autowired
    private DoctorSpecialtyRepository doctorSpecialtyRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public DoctorSpecialtyResponse addMapping(Long doctorId, Long specialtyId) {

        // Check if mapping already exists
        List<DoctorSpecialty> existing = doctorSpecialtyRepository.findByDoctorId(doctorId);
        for (DoctorSpecialty ds : existing) {
            if (ds.getSpecialtyId().equals(specialtyId)) {
                throw new RuntimeException("Doctor is already mapped to this specialty");
            }
        }

        DoctorSpecialty mapping = new DoctorSpecialty();
        mapping.setDoctorId(doctorId);
        mapping.setSpecialtyId(specialtyId);
        DoctorSpecialty saved = doctorSpecialtyRepository.save(mapping);

        return mapToResponse(saved);
    }

    public List<DoctorSpecialtyResponse> getByDoctorId(Long doctorId) {
        List<DoctorSpecialty> mappings = doctorSpecialtyRepository.findByDoctorId(doctorId);

        if (mappings.isEmpty()) {
            throw new RuntimeException("No specialties found for doctor with id: " + doctorId);
        }

        List<DoctorSpecialtyResponse> result = new ArrayList<>();
        for (DoctorSpecialty ds : mappings) {
            result.add(mapToResponse(ds));
        }
        return result;
    }

    public List<DoctorSpecialtyResponse> getBySpecialtyId(Long specialtyId) {
        List<DoctorSpecialty> mappings = doctorSpecialtyRepository.findBySpecialtyId(specialtyId);

        if (mappings.isEmpty()) {
            throw new RuntimeException("No doctors found for specialty with id: " + specialtyId);
        }

        List<DoctorSpecialtyResponse> result = new ArrayList<>();
        for (DoctorSpecialty ds : mappings) {
            result.add(mapToResponse(ds));
        }
        return result;
    }

    public void deleteByDoctorId(Long doctorId) {
        List<DoctorSpecialty> existing = doctorSpecialtyRepository.findByDoctorId(doctorId);
        if (existing.isEmpty()) {
            throw new RuntimeException("No specialty mappings found for doctor with id: " + doctorId);
        }
        doctorSpecialtyRepository.deleteByDoctorId(doctorId);
    }

    private DoctorSpecialtyResponse mapToResponse(DoctorSpecialty ds) {
        Specialty specialty = specialtyRepository.findById(ds.getSpecialtyId())
                .orElse(null);

        String name = specialty != null ? specialty.getName() : "Unknown";
        String description = specialty != null ? specialty.getDescription() : "Unknown";

        return new DoctorSpecialtyResponse(
                ds.getId(),
                ds.getDoctorId(),
                ds.getSpecialtyId(),
                name,
                description
        );
    }
}