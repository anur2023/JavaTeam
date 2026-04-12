package com.doctor.appoint.modules.doctor.dto.request;

public class SpecialtyDTO {

    private Long specialtyId;
    private String name;
    private String description;

    public SpecialtyDTO() {
    }

    public SpecialtyDTO(Long specialtyId, String name, String description) {
        this.specialtyId = specialtyId;
        this.name = name;
        this.description = description;
    }

    public Long getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}