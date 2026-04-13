package com.example.OnlineStationary.module.product.repository;

import com.example.OnlineStationary.module.product.entity.Inventory;
import com.example.OnlineStationary.module.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProduct(Product product);
}