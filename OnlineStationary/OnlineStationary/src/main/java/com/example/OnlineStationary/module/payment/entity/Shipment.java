package com.example.OnlineStationary.module.payment.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    @Column(nullable = false)
    private Long orderId;

    @Column(unique = true)
    private String trackingNumber;

    @Column(nullable = false)
    private String status; // PENDING, SHIPPED, OUT_FOR_DELIVERY, DELIVERED

    private LocalDateTime estimatedDelivery;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Default Constructor
    public Shipment() {
    }

    // Parameterized Constructor
    public Shipment(Long orderId, String trackingNumber, String status,
                    LocalDateTime estimatedDelivery, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.estimatedDelivery = estimatedDelivery;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getShipmentId() {
        return shipmentId;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}