package com.doctor.appoint.modules.appointment.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "availability_slots")
public class AvailabilitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "slot_date", nullable = false)
    private LocalDate slotDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    // ONLINE or OFFLINE
    @Column(name = "mode", nullable = false)
    private String mode;

    @Column(name = "is_booked", nullable = false)
    private boolean isBooked = false;

    public AvailabilitySlot() {}

    public Long getId()            { return id; }
    public Long getDoctorId()      { return doctorId; }
    public LocalDate getSlotDate() { return slotDate; }
    public LocalTime getStartTime(){ return startTime; }
    public LocalTime getEndTime()  { return endTime; }
    public String getMode()        { return mode; }
    public boolean isBooked()      { return isBooked; }

    public void setId(Long id)                  { this.id = id; }
    public void setDoctorId(Long doctorId)      { this.doctorId = doctorId; }
    public void setSlotDate(LocalDate slotDate) { this.slotDate = slotDate; }
    public void setStartTime(LocalTime t)       { this.startTime = t; }
    public void setEndTime(LocalTime t)         { this.endTime = t; }
    public void setMode(String mode)            { this.mode = mode; }
    public void setBooked(boolean booked)       { isBooked = booked; }
}