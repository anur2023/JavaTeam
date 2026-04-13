package com.example.OnlineStationary.module.address.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AddressRequest {

    @NotBlank private String street;
    @NotBlank private String city;
    private String state;
    @NotBlank private String postalCode;
    @NotBlank private String country;
    private boolean isDefault;

    public String getStreet() { return street; }
    public void setStreet(String s) { this.street = s; }
    public String getCity() { return city; }
    public void setCity(String c) { this.city = c; }
    public String getState() { return state; }
    public void setState(String s) { this.state = s; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String p) { this.postalCode = p; }
    public String getCountry() { return country; }
    public void setCountry(String c) { this.country = c; }
    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean d) { this.isDefault = d; }
}