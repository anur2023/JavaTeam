package com.example.OnlineStationary.address.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    @Column(name = "postal_code") private String postalCode;
    private String country;
    @Column(name = "is_default")  private boolean isDefault;
    @Column(name = "user_id")     private Long userId;

    public Long getId() { return id; }
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
    public Long getUserId() { return userId; }
    public void setUserId(Long uid) { this.userId = uid; }
}