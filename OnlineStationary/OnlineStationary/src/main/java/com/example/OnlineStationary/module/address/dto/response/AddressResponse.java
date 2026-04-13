package com.example.OnlineStationary.address.dto.response;

public class AddressResponse {
    private Long id;
    private String street, city, state, postalCode, country;
    private boolean isDefault;

    public AddressResponse(Long id, String street, String city, String state,
                           String postalCode, String country, boolean isDefault) {
        this.id = id; this.street = street; this.city = city; this.state = state;
        this.postalCode = postalCode; this.country = country; this.isDefault = isDefault;
    }

    public Long getId() { return id; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }
    public boolean isDefault() { return isDefault; }
}