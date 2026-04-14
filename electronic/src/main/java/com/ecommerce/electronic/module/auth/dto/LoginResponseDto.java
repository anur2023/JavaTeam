package com.ecommerce.electronic.module.auth.dto;

import com.ecommerce.electronic.module.auth.entity.Role;

public class LoginResponseDto {

    private String token;
    private Role role;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String token, Role role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Role getRole() {
        return role;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}