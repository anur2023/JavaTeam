package com.doctor.appoint.modules.doctor.controller;

import com.doctor.appoint.modules.doctor.entity.DoctorSpecialty;
import com.doctor.appoint.modules.doctor.service.DoctorSpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor-specialties")
public class DoctorSpecialtyController {

    @Autowired
    private DoctorSpecialtyService doctorSpecialtyService;

    @PostMapping
    public DoctorSpecialty addMapping(@RequestParam Long doctorId,
                                      @RequestParam Long specialtyId) {
        return doctorSpecialtyService.addMapping(doctorId, specialtyId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<DoctorSpecialty> getByDoctorId(@PathVariable Long doctorId) {
        return doctorSpecialtyService.getByDoctorId(doctorId);
    }

    @GetMapping("/specialty/{specialtyId}")
    public List<DoctorSpecialty> getBySpecialtyId(@PathVariable Long specialtyId) {
        return doctorSpecialtyService.getBySpecialtyId(specialtyId);
    }

    @DeleteMapping("/doctor/{doctorId}")
    public void deleteByDoctorId(@PathVariable Long doctorId) {
        doctorSpecialtyService.deleteByDoctorId(doctorId);
    }
}