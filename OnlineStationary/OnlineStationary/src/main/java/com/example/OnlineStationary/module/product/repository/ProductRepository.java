package com.example.OnlineStationary.module.product.repository;

import com.example.OnlineStationary.module.product.entity.Brand;
import com.example.OnlineStationary.module.product.entity.Category;
import com.example.OnlineStationary.module.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findByBrand(Brand brand);
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByCategoryAndBrand(Category category, Brand brand);
}