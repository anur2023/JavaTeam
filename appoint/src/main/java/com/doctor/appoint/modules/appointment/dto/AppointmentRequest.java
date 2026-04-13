package com.doctor.appoint.modules.appointment.dto;

public class AppointmentRequest {

    private Long slotId;
    private String notes;

    public Long getSlotId() { return slotId; }
    public String getNotes() { return notes; }

    public void setSlotId(Long slotId) { this.slotId = slotId; }
    public void setNotes(String notes) { this.notes = notes; }
}