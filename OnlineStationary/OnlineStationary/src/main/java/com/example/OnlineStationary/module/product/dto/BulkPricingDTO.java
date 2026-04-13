package com.example.OnlineStationary.module.product.dto;

public class BulkPricingDTO {

    private Long bulkId;
    private Long productId;
    private Integer minQuantity;
    private Double discountedPrice;

    public BulkPricingDTO() {
    }

    public BulkPricingDTO(Long bulkId, Long productId, Integer minQuantity, Double discountedPrice) {
        this.bulkId = bulkId;
        this.productId = productId;
        this.minQuantity = minQuantity;
        this.discountedPrice = discountedPrice;
    }

    public Long getBulkId() {
        return bulkId;
    }

    public void setBulkId(Long bulkId) {
        this.bulkId = bulkId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}