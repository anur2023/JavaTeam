package com.example.OnlineStationary.module.payment.controller;

import com.example.OnlineStationary.module.payment.dto.SubscriptionDTO;
import com.example.OnlineStationary.module.payment.entity.Subscription;
import com.example.OnlineStationary.module.payment.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // Create Subscription
    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionDTO dto) {
        Subscription subscription = subscriptionService.createSubscription(dto);
        return ResponseEntity.ok(subscription);
    }

    // Get all subscriptions by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscription>> getByUser(@PathVariable Long userId) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }

    // Get all subscriptions by Product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Subscription>> getByProduct(@PathVariable Long productId) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByProductId(productId);
        return ResponseEntity.ok(subscriptions);
    }
}