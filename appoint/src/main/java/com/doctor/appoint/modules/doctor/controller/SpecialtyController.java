package com.doctor.appoint.modules.doctor.controller;

import com.doctor.appoint.modules.doctor.dto.request.SpecialtyDTO;
import com.doctor.appoint.modules.doctor.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @PostMapping
    public SpecialtyDTO createSpecialty(@RequestBody SpecialtyDTO dto) {
        return specialtyService.createSpecialty(dto);
    }

    @GetMapping
    public List<SpecialtyDTO> getAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    @GetMapping("/{id}")
    public SpecialtyDTO getSpecialtyById(@PathVariable Long id) {
        return specialtyService.getSpecialtyById(id);
    }
}