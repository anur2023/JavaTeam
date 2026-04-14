package com.ecommerce.electronic.module.auth.dto;

import com.ecommerce.electronic.module.auth.entity.Role;

public class RegisterRequestDto {

    private String name;
    private String email;
    private String password;
    private Role role;
    private String phone;

    public RegisterRequestDto() {
    }

    public RegisterRequestDto(String name, String email, String password, Role role, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}