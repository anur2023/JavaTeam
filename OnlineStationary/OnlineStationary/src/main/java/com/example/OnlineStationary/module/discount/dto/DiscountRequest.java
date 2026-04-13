package com.example.OnlineStationary.module.discount.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DiscountRequest {

    private String type;
    private BigDecimal value;
    private String couponCode;
    private LocalDate validFrom;
    private LocalDate validTo;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
    public LocalDate getValidFrom() { return validFrom; }
    public void setValidFrom(LocalDate validFrom) { this.validFrom = validFrom; }
    public LocalDate getValidTo() { return validTo; }
    public void setValidTo(LocalDate validTo) { this.validTo = validTo; }
}