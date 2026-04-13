package com.example.OnlineStationary.module.discount.dto;

import java.math.BigDecimal;

public class CouponValidateRequest {

    private String couponCode;
    private BigDecimal totalAmount;

    // Getters and Setters
    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}