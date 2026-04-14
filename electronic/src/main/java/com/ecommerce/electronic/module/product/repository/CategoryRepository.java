package com.ecommerce.electronic.module.product.repository;

import com.ecommerce.electronic.module.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}