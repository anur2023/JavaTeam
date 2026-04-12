package com.doctor.appoint.modules.auth.controller;

import com.doctor.appoint.modules.auth.dto.AuthResponse;
import com.doctor.appoint.modules.auth.dto.LoginRequest;
import com.doctor.appoint.modules.auth.dto.LoginResponse;
import com.doctor.appoint.modules.auth.dto.RegisterRequest;
import com.doctor.appoint.modules.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}