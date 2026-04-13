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
        Users patient = userRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        AvailabilitySlot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.isBooked()) {
            throw new RuntimeException("Slot is already booked");
        }
        if (appointmentRepository.existsBySlotId(slot.getId())) {
            throw new RuntimeException("Appointment already exists for this slot");
        }

        slot.setBooked(true);
        slotRepository.save(slot);

        Appointment appointment = new Appointment();
        appointment.setPatientId(patient.getId());
        appointment.setDoctorId(slot.getDoctorId());
        appointment.setSlot(slot);
        appointment.setMode(slot.getMode()); // mode inherited from slot
        appointment.setNotes(request.getNotes());
        appointment.setStatus(AppointmentStatus.PENDING);

        return mapToResponse(appointmentRepository.save(appointment));
    }

    public List<AppointmentResponse> getAppointmentsForUser(String patientEmail) {
        Users patient = userRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return appointmentRepository.findByPatientId(patient.getId())
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public AppointmentResponse updateStatus(Long appointmentId, String newStatus) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        try {
            AppointmentStatus status = AppointmentStatus.valueOf(newStatus.toUpperCase());
            appointment.setStatus(status);
            if (status == AppointmentStatus.CANCELLED) {
                AvailabilitySlot slot = appointment.getSlot();
                slot.setBooked(false);
                slotRepository.save(slot);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + newStatus +
                    ". Valid: PENDING, CONFIRMED, CANCELLED, COMPLETED, NO_SHOW");
        }

        return mapToResponse(appointmentRepository.save(appointment));
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
                a.getMode(),
                a.getStatus(),
                a.getNotes(),
                a.getCreatedAt()
        );
    }
}