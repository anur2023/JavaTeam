package com.ecommerce.electronic.module.product.service;

import com.ecommerce.electronic.module.product.dto.CategoryRequest;
import com.ecommerce.electronic.module.product.dto.CategoryResponse;
import com.ecommerce.electronic.module.product.entity.Category;
import com.ecommerce.electronic.module.product.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ── CREATE ───────────────────────────────────────────────────────────────
    public CategoryResponse createCategory(CategoryRequest request) {
        boolean exists = categoryRepository.existsByNameIgnoreCase(request.getName());
        if (exists) {
            throw new RuntimeException("Category already exists: " + request.getName());
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return toResponse(categoryRepository.save(category));
    }

    // ── READ ALL ─────────────────────────────────────────────────────────────
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── READ BY ID ───────────────────────────────────────────────────────────
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
        return toResponse(category);
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return toResponse(categoryRepository.save(category));
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }

    // ── MAPPER ───────────────────────────────────────────────────────────────
    private CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }
}