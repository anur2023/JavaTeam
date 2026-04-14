package com.ecommerce.electronic.module.order.dto;

import java.util.List;

public class OrderRequestDto {

    private Long userId;
    private List<OrderItemDto> items;

    public OrderRequestDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}