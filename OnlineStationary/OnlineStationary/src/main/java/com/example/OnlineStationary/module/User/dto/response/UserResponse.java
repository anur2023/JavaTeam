package com.example.OnlineStationary.module.User.dto.response;

import java.time.LocalDateTime;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private boolean active;
    private LocalDateTime createdAt;

    public UserResponse(Long id, String username, String email, String fullName,
                        String phoneNumber, boolean active, LocalDateTime createdAt) {
        this.id = id; this.username = username; this.email = email;
        this.fullName = fullName; this.phoneNumber = phoneNumber;
        this.active = active; this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}