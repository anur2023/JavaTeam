package com.doctor.appoint.modules.auth.service;

import com.doctor.appoint.modules.auth.Entity.Role;
import com.doctor.appoint.modules.auth.Entity.Users;
import com.doctor.appoint.modules.auth.dto.AuthResponse;
import com.doctor.appoint.modules.auth.dto.LoginRequest;
import com.doctor.appoint.modules.auth.dto.LoginResponse;
import com.doctor.appoint.modules.auth.dto.RegisterRequest;
import com.doctor.appoint.modules.auth.repository.UserRepository;
import com.doctor.appoint.common.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        userRepository.save(user);
//        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse( user.getRole().name(), user.getName());
    }

    public LoginResponse login(LoginRequest request) {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponse(token, user.getRole().name(), user.getName());
    }

    public Users getProfile(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}