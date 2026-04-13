package com.example.OnlineStationary.module.cart.repository;

import com.example.OnlineStationary.module.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserUserId(Long userId);
}