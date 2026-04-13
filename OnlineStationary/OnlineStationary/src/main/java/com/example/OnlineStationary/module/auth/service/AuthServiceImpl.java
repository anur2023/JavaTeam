package com.example.OnlineStationary.module.auth.service;

import com.example.OnlineStationary.common.security.JwtUtils;
import com.example.OnlineStationary.module.auth.dto.request.LoginRequest;
import com.example.OnlineStationary.module.auth.dto.request.RegisterRequest;
import com.example.OnlineStationary.module.auth.dto.response.AuthResponse;
import com.example.OnlineStationary.module.auth.entity.Role;
import com.example.OnlineStationary.module.auth.entity.UserAuth;
import com.example.OnlineStationary.module.auth.repository.RoleRepository;
import com.example.OnlineStationary.module.auth.repository.UserAuthRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserAuthRepository userAuthRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserAuthRepository userAuthRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils) {
        this.userAuthRepository = userAuthRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userAuthRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        if (userAuthRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        UserAuth user = new UserAuth();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRoles(Set.of(userRole));

        UserAuth savedUser = userAuthRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);

        return new AuthResponse(token, savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);

        UserAuth user = userAuthRepository
                .findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail());
    }
}