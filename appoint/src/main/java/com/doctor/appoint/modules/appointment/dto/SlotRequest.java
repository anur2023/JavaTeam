package com.doctor.appoint.modules.appointment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;

public class SlotRequest {

    @NotNull
    private Long doctorId;

    @NotNull
    private LocalDate slotDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotBlank
    @Pattern(regexp = "ONLINE|OFFLINE", message = "Mode must be ONLINE or OFFLINE")
    private String mode;

    public Long getDoctorId()      { return doctorId; }
    public LocalDate getSlotDate() { return slotDate; }
    public LocalTime getStartTime(){ return startTime; }
    public LocalTime getEndTime()  { return endTime; }
    public String getMode()        { return mode; }

    public void setDoctorId(Long doctorId)      { this.doctorId = doctorId; }
    public void setSlotDate(LocalDate slotDate) { this.slotDate = slotDate; }
    public void setStartTime(LocalTime t)       { this.startTime = t; }
    public void setEndTime(LocalTime t)         { this.endTime = t; }
    public void setMode(String mode)            { this.mode = mode; }
}