package com.doctor.appoint.modules.admin.dto;

public class SpecialtyStatsDTO {

    private String specialtyName;
    private Long appointmentCount;

    public SpecialtyStatsDTO() {
    }

    public SpecialtyStatsDTO(String specialtyName, Long appointmentCount) {
        this.specialtyName = specialtyName;
        this.appointmentCount = appointmentCount;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public Long getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(Long appointmentCount) {
        this.appointmentCount = appointmentCount;
    }
}