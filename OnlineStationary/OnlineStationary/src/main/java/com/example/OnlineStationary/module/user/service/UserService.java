package com.example.OnlineStationary.module.user.service;

import com.example.OnlineStationary.module.user.dto.request.UpdateUserRequest;
import com.example.OnlineStationary.module.user.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest request);
}