package com.doctor.appoint.modules.doctor.service;

import com.doctor.appoint.modules.doctor.dto.request.SpecialtyDTO;
import com.doctor.appoint.modules.doctor.entity.Specialty;
import com.doctor.appoint.modules.doctor.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public SpecialtyDTO createSpecialty(SpecialtyDTO dto) {

        Specialty specialty = new Specialty();
        specialty.setName(dto.getName());
        specialty.setDescription(dto.getDescription());

        Specialty saved = specialtyRepository.save(specialty);

        return mapToDTO(saved);
    }

    public List<SpecialtyDTO> getAllSpecialties() {

        List<Specialty> list = specialtyRepository.findAll();
        List<SpecialtyDTO> result = new ArrayList<>();

        for (Specialty s : list) {
            result.add(mapToDTO(s));
        }

        return result;
    }

    public SpecialtyDTO getSpecialtyById(Long id) {

        Optional<Specialty> optional = specialtyRepository.findById(id);

        if (optional.isPresent()) {
            return mapToDTO(optional.get());
        } else {
            throw new RuntimeException("Specialty not found");
        }
    }

    private SpecialtyDTO mapToDTO(Specialty s) {

        SpecialtyDTO dto = new SpecialtyDTO();
        dto.setSpecialtyId(s.getSpecialtyId());
        dto.setName(s.getName());
        dto.setDescription(s.getDescription());

        return dto;
    }
}