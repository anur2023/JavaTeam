package com.doctor.appoint.modules.doctor.controller;

import com.doctor.appoint.modules.appointment.dto.AppointmentResponse;
import com.doctor.appoint.modules.appointment.repository.AppointmentRepository;
import com.doctor.appoint.modules.appointment.service.AppointmentService;
import com.doctor.appoint.modules.auth.Entity.Users;
import com.doctor.appoint.modules.auth.repository.UserRepository;
import com.doctor.appoint.modules.doctor.entity.Doctor;
import com.doctor.appoint.modules.doctor.repository.DoctorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor-appointments")
@PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
public class DoctorAppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public DoctorAppointmentController(AppointmentRepository appointmentRepository,
                                       AppointmentService appointmentService,
                                       UserRepository userRepository,
                                       DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/my")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments(Authentication authentication) {
        String email = (String) authentication.getPrincipal();

        // Step 1: Find logged-in user
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "User not found with email: " + email));

        // Step 2: Find doctor profile linked to this user
        Doctor doctor = doctorRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Doctor profile not found for userId: " + user.getId()
                                + ". Please create a doctor profile first via POST /doctors"));

        // Step 3: Fetch appointments by doctorId
        List<AppointmentResponse> responses = appointmentRepository
                .findByDoctorId(doctor.getDoctorId())
                .stream()
                .map(a -> new AppointmentResponse(
                        a.getId(),
                        a.getPatientId(),
                        a.getDoctorId(),
                        a.getSlot().getId(),
                        a.getSlot().getSlotDate(),
                        a.getSlot().getStartTime(),
                        a.getSlot().getEndTime(),
                        a.getMode(),
                        a.getStatus(),
                        a.getNotes(),
                        a.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        return ResponseEntity.ok(appointmentService.updateStatus(id, newStatus));
    }
}