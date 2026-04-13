package com.example.OnlineStationary.module.payment.dto;

public class PaymentDTO {

    private Long orderId;
    private String paymentMethod;
    private String status;
    private String transactionId;

    // Default Constructor
    public PaymentDTO() {
    }

    // Parameterized Constructor
    public PaymentDTO(Long orderId, String paymentMethod, String status, String transactionId) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionId = transactionId;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}