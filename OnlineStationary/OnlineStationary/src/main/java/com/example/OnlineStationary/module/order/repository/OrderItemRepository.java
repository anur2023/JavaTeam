package com.example.OnlineStationary.module.order.repository;

import com.example.OnlineStationary.module.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}