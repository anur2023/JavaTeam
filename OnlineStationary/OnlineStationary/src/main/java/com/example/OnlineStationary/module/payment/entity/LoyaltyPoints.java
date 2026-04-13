package com.example.OnlineStationary.module.payment.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "loyalty_points")
public class LoyaltyPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loyaltyId;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Integer points;

    // Default Constructor
    public LoyaltyPoints() {
    }

    // Parameterized Constructor
    public LoyaltyPoints(Long userId, Integer points) {
        this.userId = userId;
        this.points = points;
    }

    // Getters and Setters
    public Long getLoyaltyId() {
        return loyaltyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}