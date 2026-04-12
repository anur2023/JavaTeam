package com.doctor.appoint.modules.appointment.service;

import com.doctor.appoint.modules.appointment.dto.AppointmentRequest;
import com.doctor.appoint.modules.appointment.dto.AppointmentResponse;
import com.doctor.appoint.modules.appointment.entity.Appointment;
import com.doctor.appoint.modules.appointment.entity.AvailabilitySlot;
import com.doctor.appoint.modules.appointment.enums.AppointmentStatus;
import com.doctor.appoint.modules.appointment.repository.AppointmentRepository;
import com.doctor.appoint.modules.appointment.repository.AvailabilitySlotRepository;
import com.doctor.appoint.modules.auth.Entity.Users;
import com.doctor.appoint.modules.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilitySlotRepository slotRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              AvailabilitySlotRepository slotRepository,
                              UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.slotRepository = slotRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AppointmentResponse bookAppointment(String patientEmail, AppointmentRequest request) {

        // 1. Resolve patient from JWT email
        Users patient = userRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // 2. Find the slot
        AvailabilitySlot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        // 3. Prevent double booking
        if (slot.isBooked()) {
            throw new RuntimeException("Slot is already booked");
        }

        // 4. Extra guard at appointment level
        if (appointmentRepository.existsBySlotId(slot.getId())) {
            throw new RuntimeException("Appointment already exists for this slot");
        }

        // 5. Mark slot as booked
        slot.setBooked(true);
        slotRepository.save(slot);

        // 6. Create appointment
        Appointment appointment = new Appointment();
        appointment.setPatientId(patient.getId());
        appointment.setDoctorId(slot.getDoctorId());
        appointment.setSlot(slot);
        appointment.setNotes(request.getNotes());
        appointment.setStatus(AppointmentStatus.PENDING);

        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    public List<AppointmentResponse> getAppointmentsForUser(String patientEmail) {
        Users patient = userRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return appointmentRepository.findByPatientId(patient.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AppointmentResponse updateStatus(Long appointmentId, String newStatus) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        try {
            AppointmentStatus status = AppointmentStatus.valueOf(newStatus.toUpperCase());
            appointment.setStatus(status);

            // If cancelled, free the slot back up
            if (status == AppointmentStatus.CANCELLED) {
                AvailabilitySlot slot = appointment.getSlot();
                slot.setBooked(false);
                slotRepository.save(slot);
            }

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + newStatus +
                    ". Valid values: PENDING, CONFIRMED, CANCELLED, COMPLETED");
        }

        Appointment updated = appointmentRepository.save(appointment);
        return mapToResponse(updated);
    }

    private AppointmentResponse mapToResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getPatientId(),
                a.getDoctorId(),
                a.getSlot().getId(),
                a.getSlot().getSlotDate(),
                a.getSlot().getStartTime(),
                a.getSlot().getEndTime(),
                a.getStatus(),
                a.getNotes(),
                a.getCreatedAt()
        );
    }
}