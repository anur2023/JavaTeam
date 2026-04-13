package com.example.OnlineStationary.module.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private Long cartId;
    private List<CartItemDetail> items;
    private BigDecimal totalAmount;

    public static class CartItemDetail {
        private Long cartItemId;
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;

        // Getters and Setters
        public Long getCartItemId() { return cartItemId; }
        public void setCartItemId(Long cartItemId) { this.cartItemId = cartItemId; }
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }

    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }
    public List<CartItemDetail> getItems() { return items; }
    public void setItems(List<CartItemDetail> items) { this.items = items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}