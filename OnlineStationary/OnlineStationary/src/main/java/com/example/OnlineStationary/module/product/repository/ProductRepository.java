package com.example.OnlineStationary.module.product.repository;

import com.example.OnlineStationary.module.product.entity.Product;
import com.example.OnlineStationary.module.product.entity.Category;
import com.example.OnlineStationary.module.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByBrand(Brand brand);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategoryAndBrand(Category category, Brand brand);
}