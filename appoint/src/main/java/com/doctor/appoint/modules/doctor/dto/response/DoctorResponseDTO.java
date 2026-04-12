package com.doctor.appoint.modules.doctor.dto.response;
//package com.doctor.appoint.modules.doctor.dto;

import java.time.LocalDateTime;

public class DoctorResponseDTO {

    private Long doctorId;
    private Long userId;
    private Integer experienceYears;
    private Double consultationFeeOnline;
    private Double consultationFeeOffline;
    private LocalDateTime createdAt;

    public DoctorResponseDTO() {
    }

    public DoctorResponseDTO(Long doctorId, Long userId, Integer experienceYears,
                             Double consultationFeeOnline, Double consultationFeeOffline,
                             LocalDateTime createdAt) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.experienceYears = experienceYears;
        this.consultationFeeOnline = consultationFeeOnline;
        this.consultationFeeOffline = consultationFeeOffline;
        this.createdAt = createdAt;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Double getConsultationFeeOnline() {
        return consultationFeeOnline;
    }

    public void setConsultationFeeOnline(Double consultationFeeOnline) {
        this.consultationFeeOnline = consultationFeeOnline;
    }

    public Double getConsultationFeeOffline() {
        return consultationFeeOffline;
    }

    public void setConsultationFeeOffline(Double consultationFeeOffline) {
        this.consultationFeeOffline = consultationFeeOffline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}