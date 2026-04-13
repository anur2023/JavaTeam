package com.doctor.appoint.modules.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotResponse {

    private Long id;
    private Long doctorId;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String mode;
    private boolean isBooked;

    public SlotResponse(Long id, Long doctorId, LocalDate slotDate,
                        LocalTime startTime, LocalTime endTime,
                        String mode, boolean isBooked) {
        this.id = id;
        this.doctorId = doctorId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.isBooked = isBooked;
    }

    public Long getId()            { return id; }
    public Long getDoctorId()      { return doctorId; }
    public LocalDate getSlotDate() { return slotDate; }
    public LocalTime getStartTime(){ return startTime; }
    public LocalTime getEndTime()  { return endTime; }
    public String getMode()        { return mode; }
    public boolean isBooked()      { return isBooked; }
}