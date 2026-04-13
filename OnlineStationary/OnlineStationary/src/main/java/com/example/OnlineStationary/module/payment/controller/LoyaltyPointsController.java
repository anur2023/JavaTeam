package com.example.OnlineStationary.module.payment.controller;

import com.example.OnlineStationary.module.payment.dto.LoyaltyPointsDTO;
import com.example.OnlineStationary.module.payment.entity.LoyaltyPoints;
import com.example.OnlineStationary.module.payment.service.LoyaltyPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loyalty")
public class LoyaltyPointsController {

    @Autowired
    private LoyaltyPointsService loyaltyPointsService;

    // Add or Update Points
    @PostMapping
    public ResponseEntity<LoyaltyPoints> addOrUpdatePoints(@RequestBody LoyaltyPointsDTO dto) {
        LoyaltyPoints points = loyaltyPointsService.addOrUpdatePoints(dto);
        return ResponseEntity.ok(points);
    }

    // Get Points by User ID
    @GetMapping("/{userId}")
    public ResponseEntity<LoyaltyPoints> getPoints(@PathVariable Long userId) {
        LoyaltyPoints points = loyaltyPointsService.getPointsByUserId(userId);
        if (points == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(points);
    }
}