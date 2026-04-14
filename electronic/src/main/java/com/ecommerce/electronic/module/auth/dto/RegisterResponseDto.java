package com.ecommerce.electronic.module.auth.dto;

import com.ecommerce.electronic.module.auth.entity.Role;

public class RegisterResponseDto {

    private Long id;
    private String name;
    private String email;
    private Role role;
    private String phone;

    public RegisterResponseDto() {
    }

    public RegisterResponseDto(Long id, String name, String email, Role role, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}