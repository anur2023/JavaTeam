package com.example.OnlineStationary.module.discount.dto;

import java.math.BigDecimal;

public class CouponValidateResponse {

    private boolean valid;
    private String message;
    private BigDecimal originalAmount;
    private BigDecimal discountedAmount;
    private BigDecimal savings;

    // Getters and Setters
    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public BigDecimal getOriginalAmount() { return originalAmount; }
    public void setOriginalAmount(BigDecimal originalAmount) { this.originalAmount = originalAmount; }
    public BigDecimal getDiscountedAmount() { return discountedAmount; }
    public void setDiscountedAmount(BigDecimal discountedAmount) { this.discountedAmount = discountedAmount; }
    public BigDecimal getSavings() { return savings; }
    public void setSavings(BigDecimal savings) { this.savings = savings; }
}