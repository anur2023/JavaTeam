package com.example.OnlineStationary.module.order.repository;

import com.example.OnlineStationary.module.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserUserIdOrderByCreatedAtDesc(Long userId);
}