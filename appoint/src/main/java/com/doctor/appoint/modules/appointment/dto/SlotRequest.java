package com.doctor.appoint.modules.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotRequest {

    private Long doctorId;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public Long getDoctorId() { return doctorId; }
    public LocalDate getSlotDate() { return slotDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public void setSlotDate(LocalDate slotDate) { this.slotDate = slotDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}