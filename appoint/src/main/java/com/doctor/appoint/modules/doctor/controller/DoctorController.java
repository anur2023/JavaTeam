package com.doctor.appoint.modules.doctor.controller;

import com.doctor.appoint.modules.doctor.dto.request.DoctorRequestDTO;
import com.doctor.appoint.modules.doctor.dto.response.DoctorResponseDTO;
import com.doctor.appoint.modules.doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public DoctorResponseDTO createDoctor(@RequestBody DoctorRequestDTO requestDTO) {
        return doctorService.createDoctor(requestDTO);
    }
    @GetMapping("/specialty/{specialtyId}")
    public List<DoctorResponseDTO> getDoctorsBySpecialty(@PathVariable Long specialtyId) {
        return doctorService.getDoctorsBySpecialty(specialtyId);
    }

    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public DoctorResponseDTO getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/user/{userId}")
    public DoctorResponseDTO getDoctorByUserId(@PathVariable Long userId) {
        return doctorService.getDoctorByUserId(userId);
    }
}