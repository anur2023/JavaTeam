package com.example.OnlineStationary.module.user.dto.request;

import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(min = 2, max = 50)
    private String fullName;
    private String phoneNumber;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}