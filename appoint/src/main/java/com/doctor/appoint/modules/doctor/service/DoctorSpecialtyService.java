package com.doctor.appoint.modules.doctor.service;

import com.doctor.appoint.modules.doctor.entity.DoctorSpecialty;
import com.doctor.appoint.modules.doctor.repository.DoctorSpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorSpecialtyService {

    @Autowired
    private DoctorSpecialtyRepository doctorSpecialtyRepository;

    public DoctorSpecialty addMapping(Long doctorId, Long specialtyId) {

        DoctorSpecialty mapping = new DoctorSpecialty();
        mapping.setDoctorId(doctorId);
        mapping.setSpecialtyId(specialtyId);

        return doctorSpecialtyRepository.save(mapping);
    }

    public List<DoctorSpecialty> getByDoctorId(Long doctorId) {
        return doctorSpecialtyRepository.findByDoctorId(doctorId);
    }

    public List<DoctorSpecialty> getBySpecialtyId(Long specialtyId) {
        return doctorSpecialtyRepository.findBySpecialtyId(specialtyId);
    }

    public void deleteByDoctorId(Long doctorId) {
        doctorSpecialtyRepository.deleteByDoctorId(doctorId);
    }
}