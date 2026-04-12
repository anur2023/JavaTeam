package com.doctor.appoint.modules.doctor.dto.request;

public class DoctorRequestDTO {

    private Long userId;
    private Integer experienceYears;
    private Double consultationFeeOnline;
    private Double consultationFeeOffline;

    public DoctorRequestDTO() {
    }

    public DoctorRequestDTO(Long userId, Integer experienceYears,
                            Double consultationFeeOnline, Double consultationFeeOffline) {
        this.userId = userId;
        this.experienceYears = experienceYears;
        this.consultationFeeOnline = consultationFeeOnline;
        this.consultationFeeOffline = consultationFeeOffline;
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
}