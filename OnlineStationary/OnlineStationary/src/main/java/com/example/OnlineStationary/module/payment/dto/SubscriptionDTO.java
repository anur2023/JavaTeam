package com.example.OnlineStationary.module.payment.dto;

import java.time.LocalDateTime;

public class SubscriptionDTO {

    private Long userId;
    private Long productId;
    private String frequency;
    private LocalDateTime nextDelivery;

    // Default Constructor
    public SubscriptionDTO() {
    }

    // Parameterized Constructor
    public SubscriptionDTO(Long userId, Long productId, String frequency, LocalDateTime nextDelivery) {
        this.userId = userId;
        this.productId = productId;
        this.frequency = frequency;
        this.nextDelivery = nextDelivery;
    }

    // Getters and Setters
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
}