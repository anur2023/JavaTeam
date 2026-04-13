package com.example.OnlineStationary.module.discount.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DiscountRequest {

    private String type;         // SEASONAL or COUPON
    private BigDecimal value;
    private String couponCode;   // null if SEASONAL
    private LocalDate validFrom;
    private LocalDate validTo;

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    public String getCouponCode() { return couponCode; }
    public void setCouponCode(StringDiscountRequest couponCode) { this.couponCode = couponCode; }
    public LocalDate getValidFrom() { return validFrom; }
    public void setValidFrom(LocalDate validFrom) { this.validFrom = validFrom; }
    public LocalDate getValidTo() { return validTo; }
    public void setValidTo(LocalDate validTo) { this.validTo = validTo; }
}