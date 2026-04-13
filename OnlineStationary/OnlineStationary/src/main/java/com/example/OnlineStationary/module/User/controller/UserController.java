package com.example.OnlineStationary.user.controller;

import com.example.OnlineStationary.module.User.dto.request.UpdateUserRequest;
import com.example.OnlineStationary.module.User.dto.response.UserResponse;
import com.example.OnlineStationary.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateUserRequest req) {
        return ResponseEntity.ok(userService.updateUser(id, req));
    }
}