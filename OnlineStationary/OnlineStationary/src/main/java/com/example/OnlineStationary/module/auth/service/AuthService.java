package com.example.OnlineStationary.auth.service;

import com.example.OnlineStationary.auth.dto.request.LoginRequest;
import com.example.OnlineStationary.auth.dto.request.RegisterRequest;
import com.example.OnlineStationary.auth.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}