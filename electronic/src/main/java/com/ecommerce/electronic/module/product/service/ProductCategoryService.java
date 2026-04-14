package com.ecommerce.electronic.module.product.service;

import com.ecommerce.electronic.module.product.dto.ProductCategoryRequest;
import com.ecommerce.electronic.module.product.dto.ProductCategoryResponse;
import com.ecommerce.electronic.module.product.entity.Category;
import com.ecommerce.electronic.module.product.entity.Product;
import com.ecommerce.electronic.module.product.entity.ProductCategory;
import com.ecommerce.electronic.module.product.repository.CategoryRepository;
import com.ecommerce.electronic.module.product.repository.ProductCategoryRepository;
import com.ecommerce.electronic.module.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository,
                                  ProductRepository productRepository,
                                  CategoryRepository categoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // ── ASSIGN CATEGORY TO PRODUCT ───────────────────────────────────────────
    public ProductCategoryResponse assignCategoryToProduct(ProductCategoryRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + request.getProductId()));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));

        boolean alreadyMapped = productCategoryRepository
                .existsByProductIdAndCategoryId(request.getProductId(), request.getCategoryId());

        if (alreadyMapped) {
            throw new RuntimeException("This product is already assigned to this category.");
        }

        ProductCategory productCategory = new ProductCategory(product, category);

        return toResponse(productCategoryRepository.save(productCategory));
    }

    // ── GET ALL CATEGORIES FOR A PRODUCT ────────────────────────────────────
    public List<ProductCategoryResponse> getCategoriesForProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found: " + productId);
        }

        return productCategoryRepository.findByProductId(productId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── GET ALL PRODUCTS FOR A CATEGORY ─────────────────────────────────────
    public List<ProductCategoryResponse> getProductsForCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found: " + categoryId);
        }

        return productCategoryRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── GET ALL MAPPINGS ─────────────────────────────────────────────────────
    public List<ProductCategoryResponse> getAllMappings() {
        return productCategoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── REMOVE CATEGORY FROM PRODUCT ────────────────────────────────────────
    @Transactional
    public void removeCategoryFromProduct(Long productId, Long categoryId) {
        ProductCategory productCategory = productCategoryRepository
                .findByProductIdAndCategoryId(productId, categoryId)
                .orElseThrow(() -> new RuntimeException(
                        "Mapping not found for productId: " + productId + " and categoryId: " + categoryId));

        productCategoryRepository.delete(productCategory);
    }

    // ── REMOVE ALL CATEGORIES FROM A PRODUCT ────────────────────────────────
    @Transactional
    public void removeAllCategoriesFromProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found: " + productId);
        }
        productCategoryRepository.deleteByProductId(productId);
    }

    // ── MAPPER ───────────────────────────────────────────────────────────────
    private ProductCategoryResponse toResponse(ProductCategory pc) {
        ProductCategoryResponse response = new ProductCategoryResponse();
        response.setId(pc.getId());
        response.setProductId(pc.getProduct().getId());
        response.setProductName(pc.getProduct().getName());
        response.setCategoryId(pc.getCategory().getId());
        response.setCategoryName(pc.getCategory().getName());
        return response;
    }
}