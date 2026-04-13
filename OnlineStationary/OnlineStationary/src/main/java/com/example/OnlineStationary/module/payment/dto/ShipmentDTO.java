package com.example.OnlineStationary.module.payment.dto;

import java.time.LocalDateTime;

public class ShipmentDTO {

    private Long orderId;
    private String trackingNumber;
    private String status;
    private LocalDateTime estimatedDelivery;

    // Default Constructor
    public ShipmentDTO() {
    }

    // Parameterized Constructor
    public ShipmentDTO(Long orderId, String trackingNumber, String status, LocalDateTime estimatedDelivery) {
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.estimatedDelivery = estimatedDelivery;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }
}