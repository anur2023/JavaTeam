package com.example.OnlineStationary.module.product.repository;

import com.example.OnlineStationary.module.product.entity.BulkPricing;
import com.example.OnlineStationary.module.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulkPricingRepository extends JpaRepository<BulkPricing, Long> {

    List<BulkPricing> findByProduct(Product product);

    List<BulkPricing> findByProductOrderByMinQuantityAsc(Product product);
}