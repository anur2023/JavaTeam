package com.example.OnlineStationary.user.service;

import com.example.OnlineStationary.common.exception.ResourceNotFoundException;
import com.example.OnlineStationary.module.User.dto.request.UpdateUserRequest;
import com.example.OnlineStationary.module.User.dto.response.UserResponse;
import com.example.OnlineStationary.user.entity.UserProfile;
import com.example.OnlineStationary.user.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserProfileRepository repo;

    public UserServiceImpl(UserProfileRepository repo) { this.repo = repo; }

    @Override
    public UserResponse getUserById(Long id) {
        return toResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id)));
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest req) {
        UserProfile user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        if (req.getFullName() != null)    user.setFullName(req.getFullName());
        if (req.getPhoneNumber() != null) user.setPhoneNumber(req.getPhoneNumber());
        return toResponse(repo.save(user));
    }

    private UserResponse toResponse(UserProfile u) {
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail(),
                u.getFullName(), u.getPhoneNumber(), u.isActive(), u.getCreatedAt());
    }
}