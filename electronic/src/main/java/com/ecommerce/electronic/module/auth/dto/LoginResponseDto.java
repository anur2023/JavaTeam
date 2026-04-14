package com.ecommerce.electronic.module.auth.dto;

import com.ecommerce.electronic.module.auth.entity.Role;

public class LoginResponseDto {

    private String token;
    private Role role;
    private Long userId;
    private String name;
    private String email;

    public LoginResponseDto() {}

    public LoginResponseDto(String token, Role role, Long userId, String name, String email) {
        this.token = token;
        this.role = role;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}