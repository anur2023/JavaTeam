package com.ecommerce.electronic.module.product.dto;

public class ProductCategoryRequest {

    private Long productId;
    private Long categoryId;

    public ProductCategoryRequest() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}