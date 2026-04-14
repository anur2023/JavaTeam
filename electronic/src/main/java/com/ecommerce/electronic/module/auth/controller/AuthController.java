package com.ecommerce.electronic.module.auth.controller;

import com.ecommerce.electronic.module.auth.dto.LoginRequestDto;
import com.ecommerce.electronic.module.auth.dto.RegisterRequestDto;
import com.ecommerce.electronic.module.auth.service.AuthService;
import com.ecommerce.electronic.module.auth.dto.LoginResponseDto;
import com.ecommerce.electronic.module.auth.dto.RegisterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public RegisterResponseDto register(@RequestBody RegisterRequestDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto) {
        return authService.login(dto);
    }
}