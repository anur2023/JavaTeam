package com.ecommerce.electronic.module.order.dto;

import java.time.LocalDate;

public class WarrantyDto {

    private Long orderItemId;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private String status;

    public WarrantyDto() {
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}