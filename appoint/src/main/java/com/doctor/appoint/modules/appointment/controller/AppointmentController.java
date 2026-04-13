package com.doctor.appoint.modules.appointment.controller;

import com.doctor.appoint.modules.appointment.dto.AppointmentRequest;
import com.doctor.appoint.modules.appointment.dto.AppointmentResponse;
import com.doctor.appoint.modules.appointment.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Patient books an appointment
    @PostMapping
    public ResponseEntity<AppointmentResponse> book(@RequestBody AppointmentRequest request,
                                                    Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return ResponseEntity.ok(appointmentService.bookAppointment(email, request));
    }

    // Patient views their own appointments
    @GetMapping("/user")
    public ResponseEntity<List<AppointmentResponse>> getUserAppointments(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return ResponseEntity.ok(appointmentService.getAppointmentsForUser(email));
    }

    // Doctor or admin updates appointment status
    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable Long id,
                                                            @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        return ResponseEntity.ok(appointmentService.updateStatus(id, newStatus));
    }
}