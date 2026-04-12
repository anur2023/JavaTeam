package com.doctor.appoint.modules.auth.controller;

import com.doctor.appoint.modules.auth.Entity.Users;
import com.doctor.appoint.modules.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Users> getProfile(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getProfile(email));
    }
}