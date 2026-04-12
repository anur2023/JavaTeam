package com.doctor.appoint.modules.doctor.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "consultation_fee_online")
    private Double consultationFeeOnline;

    @Column(name = "consultation_fee_offline")
    private Double consultationFeeOffline;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Doctor() {
    }

    public Doctor(Long doctorId, Long userId, Integer experienceYears,
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