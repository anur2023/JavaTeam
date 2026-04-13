package com.doctor.appoint.modules.doctor.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_specialties")
public class DoctorSpecialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "specialty_id", nullable = false)
    private Long specialtyId;

    public DoctorSpecialty() {
    }

    public DoctorSpecialty(Long id, Long doctorId, Long specialtyId) {
        this.id = id;
        this.doctorId = doctorId;
        this.specialtyId = specialtyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
    }
}