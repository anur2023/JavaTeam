package com.example.OnlineStationary.module.cart.repository;

import com.example.OnlineStationary.module.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartCartIdAndProductProductId(Long cartId, Long productId);
}