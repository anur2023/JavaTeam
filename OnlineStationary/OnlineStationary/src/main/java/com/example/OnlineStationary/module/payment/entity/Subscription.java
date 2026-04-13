package com.example.OnlineStationary.module.payment.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String frequency; // DAILY, WEEKLY, MONTHLY

    private LocalDateTime nextDelivery;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Default Constructor
    public Subscription() {
    }

    // Parameterized Constructor
    public Subscription(Long userId, Long productId, String frequency,
                        LocalDateTime nextDelivery, LocalDateTime createdAt) {
        this.userId = userId;
        this.productId = productId;
        this.frequency = frequency;
        this.nextDelivery = nextDelivery;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDateTime getNextDelivery() {
        return nextDelivery;
    }

    public void setNextDelivery(LocalDateTime nextDelivery) {
        this.nextDelivery = nextDelivery;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}