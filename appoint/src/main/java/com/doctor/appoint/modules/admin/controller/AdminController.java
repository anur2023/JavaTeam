package com.doctor.appoint.modules.admin.controller;

import com.doctor.appoint.modules.admin.dto.AppointmentResponseDTO;
import com.doctor.appoint.modules.admin.dto.DailyReportDTO;
import com.doctor.appoint.modules.admin.dto.RevenueReportDTO;
import com.doctor.appoint.modules.admin.dto.SpecialtyStatsDTO;
import com.doctor.appoint.modules.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/appointments")
    public List<AppointmentResponseDTO> getAllAppointments() {
        return adminService.getAllAppointments();
    }
    @GetMapping("/reports/daily")
    public DailyReportDTO getDailyReport() {
        return adminService.getDailyReport();
    }
    @GetMapping("/revenue")
    public RevenueReportDTO getRevenueReport() {
        return adminService.getRevenueReport();
    }
    @GetMapping("/specialty-stats")
    public List<SpecialtyStatsDTO> getSpecialtyStats() {
        return adminService.getSpecialtyStats();
    }
}