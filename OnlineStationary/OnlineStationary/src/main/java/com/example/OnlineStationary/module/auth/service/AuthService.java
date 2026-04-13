package com.example.OnlineStationary.module.auth.service;

import com.example.OnlineStationary.module.auth.dto.request.LoginRequest;
import com.example.OnlineStationary.module.auth.dto.request.RegisterRequest;
import com.example.OnlineStationary.module.auth.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}