package com.example.OnlineStationary.module.payment.dto;

public class LoyaltyPointsDTO {

    private Long userId;
    private Integer points;

    // Default Constructor
    public LoyaltyPointsDTO() {
    }

    // Parameterized Constructor
    public LoyaltyPointsDTO(Long userId, Integer points) {
        this.userId = userId;
        this.points = points;
    }

    // Getters and Setters
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