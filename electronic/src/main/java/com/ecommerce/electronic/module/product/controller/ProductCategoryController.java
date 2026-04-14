package com.ecommerce.electronic.module.product.controller;

import com.ecommerce.electronic.module.product.dto.ProductCategoryRequest;
import com.ecommerce.electronic.module.product.dto.ProductCategoryResponse;
import com.ecommerce.electronic.module.product.service.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    // Assign a category to a product
    @PostMapping("/assign")
    public ResponseEntity<ProductCategoryResponse> assignCategory(
            @RequestBody ProductCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productCategoryService.assignCategoryToProduct(request));
    }

    // Get all mappings
    @GetMapping
    public ResponseEntity<List<ProductCategoryResponse>> getAllMappings() {
        return ResponseEntity.ok(productCategoryService.getAllMappings());
    }

    // Get all categories for a specific product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductCategoryResponse>> getCategoriesForProduct(
            @PathVariable Long productId) {
        return ResponseEntity.ok(productCategoryService.getCategoriesForProduct(productId));
    }

    // Get all products for a specific category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductCategoryResponse>> getProductsForCategory(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(productCategoryService.getProductsForCategory(categoryId));
    }

    // Remove a specific product-category mapping
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeCategoryFromProduct(
            @RequestParam Long productId,
            @RequestParam Long categoryId) {
        productCategoryService.removeCategoryFromProduct(productId, categoryId);
        return ResponseEntity.noContent().build();
    }

    // Remove all categories from a product
    @DeleteMapping("/remove/product/{productId}")
    public ResponseEntity<Void> removeAllCategoriesFromProduct(
            @PathVariable Long productId) {
        productCategoryService.removeAllCategoriesFromProduct(productId);
        return ResponseEntity.noContent().build();
    }
}