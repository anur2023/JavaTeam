package com.ecommerce.electronic.module.order.dto;

public class PaymentDto {

    private Long orderId;
    private Double amount;
    private String status;
    private String transactionId;

    public PaymentDto() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}