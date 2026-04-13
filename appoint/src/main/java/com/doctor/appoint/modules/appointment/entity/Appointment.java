package com.doctor.appoint.modules.appointment.entity;

import com.doctor.appoint.modules.appointment.enums.AppointmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @OneToOne
    @JoinColumn(name = "slot_id", nullable = false, unique = true)
    private AvailabilitySlot slot;

    // Inherited from slot at booking time (ONLINE / OFFLINE)
    @Column(name = "mode", nullable = false)
    private String mode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Appointment() {}

    // Standard getters
    public Long getId()                  { return id; }
    public Long getPatientId()           { return patientId; }
    public Long getDoctorId()            { return doctorId; }
    public AvailabilitySlot getSlot()    { return slot; }
    public String getMode()              { return mode; }
    public AppointmentStatus getStatus() { return status; }
    public String getNotes()             { return notes; }
    public LocalDateTime getCreatedAt()  { return createdAt; }

    // Aliases used by AdminService
    public Long getAppointmentId()        { return id; }
    public Long getSlotId()               { return slot != null ? slot.getId() : null; }
    public LocalDateTime getBookingTime() { return createdAt; }

    // Setters
    public void setId(Long id)                       { this.id = id; }
    public void setPatientId(Long patientId)         { this.patientId = patientId; }
    public void setDoctorId(Long doctorId)           { this.doctorId = doctorId; }
    public void setSlot(AvailabilitySlot slot)       { this.slot = slot; }
    public void setMode(String mode)                 { this.mode = mode; }
    public void setStatus(AppointmentStatus status)  { this.status = status; }
    public void setNotes(String notes)               { this.notes = notes; }
    public void setCreatedAt(LocalDateTime t)        { this.createdAt = t; }
}