package com.doctor.appoint.modules.admin.dto;

import com.doctor.appoint.modules.appointment.entity.AvailabilitySlot;
import com.doctor.appoint.modules.appointment.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentResponseDTO {

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private AvailabilitySlot slotId;
    private String mode;
    private
    AppointmentStatus status;
    private LocalDateTime bookingTime;

    public AppointmentResponseDTO() {
    }

    public AppointmentResponseDTO(Long appointmentId, Long patientId, Long doctorId,
                                  AvailabilitySlot slotId, String mode,
                                  AppointmentStatus status,
                                  LocalDateTime bookingTime) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.mode = mode;
        this.status = status;
        this.bookingTime = bookingTime;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public AvailabilitySlot getSlotId() {
        return slotId;
    }

    public void setSlotId(AvailabilitySlot slotId) {
        this.slotId = slotId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public
    AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}