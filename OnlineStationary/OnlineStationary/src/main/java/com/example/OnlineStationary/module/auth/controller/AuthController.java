package com.example.OnlineStationary.module.auth.controller;

import com.example.OnlineStationary.module.auth.dto.request.LoginRequest;
import com.example.OnlineStationary.module.auth.dto.request.RegisterRequest;
import com.example.OnlineStationary.module.auth.dto.response.AuthResponse;
import com.example.OnlineStationary.module.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}