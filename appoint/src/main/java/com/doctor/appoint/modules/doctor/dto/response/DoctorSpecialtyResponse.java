package com.doctor.appoint.modules.doctor.dto.response;

public class DoctorSpecialtyResponse {

    private Long id;
    private Long doctorId;
    private Long specialtyId;
    private String specialtyName;
    private String specialtyDescription;

    public DoctorSpecialtyResponse() {}

    public DoctorSpecialtyResponse(Long id, Long doctorId, Long specialtyId,
                                   String specialtyName, String specialtyDescription) {
        this.id = id;
        this.doctorId = doctorId;
        this.specialtyId = specialtyId;
        this.specialtyName = specialtyName;
        this.specialtyDescription = specialtyDescription;
    }

    public Long getId() { return id; }
    public Long getDoctorId() { return doctorId; }
    public Long getSpecialtyId() { return specialtyId; }
    public String getSpecialtyName() { return specialtyName; }
    public String getSpecialtyDescription() { return specialtyDescription; }

    public void setId(Long id) { this.id = id; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public void setSpecialtyId(Long specialtyId) { this.specialtyId = specialtyId; }
    public void setSpecialtyName(String specialtyName) { this.specialtyName = specialtyName; }
    public void setSpecialtyDescription(String specialtyDescription) { this.specialtyDescription = specialtyDescription; }
}