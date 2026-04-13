package com.example.OnlineStationary.user.service;

import com.example.OnlineStationary.module.User.dto.request.UpdateUserRequest;
import com.example.OnlineStationary.module.User.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UpdateUserRequest request);
}