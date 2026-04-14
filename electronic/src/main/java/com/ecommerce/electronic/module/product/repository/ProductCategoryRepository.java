package com.ecommerce.electronic.module.product.repository;

import com.ecommerce.electronic.module.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    // Get all category mappings for a product
    List<ProductCategory> findByProductId(Long productId);

    // Get all product mappings for a category
    List<ProductCategory> findByCategoryId(Long categoryId);

    // Check if mapping already exists
    boolean existsByProductIdAndCategoryId(Long productId, Long categoryId);

    // Find specific mapping to delete
    Optional<ProductCategory> findByProductIdAndCategoryId(Long productId, Long categoryId);

    // Delete all mappings for a product
    void deleteByProductId(Long productId);
}