package com.example.OnlineStationary.module.cart.entity;

import com.example.OnlineStationary.module.user.entity.*;
import com.example.OnlineStationary.module.user.entity.UserProfile;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    // Getters and Setters
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }
    public UserProfile getUser() { return user; }
    public void setUser(UserProfile user) { this.user = user; }
    public List<CartItem> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }
}