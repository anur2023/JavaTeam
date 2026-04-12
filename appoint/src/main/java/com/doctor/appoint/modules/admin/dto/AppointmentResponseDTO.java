package com.doctor.appoint.modules.admin.dto;

import java.time.LocalDateTime;

public class AppointmentResponseDTO {

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private Long slotId;
    private String mode;
    private String status;
    private LocalDateTime bookingTime;

    public AppointmentResponseDTO() {
    }

    public AppointmentResponseDTO(Long appointmentId, Long patientId, Long doctorId,
                                  Long slotId, String mode, String status,
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

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}