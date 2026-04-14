package com.ecommerce.electronic.module.order.repository;

import com.ecommerce.electronic.module.order.entity.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarrantyRepository extends JpaRepository<Warranty, Long> {

    List<Warranty> findByOrderItemId(Long orderItemId);

    List<Warranty> findByOrderItem_Order_UserId(Long userId);
}