package com.example.OnlineStationary.module.product.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bulk_pricing")
public class BulkPricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bulk_id")
    private Long bulkId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "min_quantity", nullable = false)
    private Integer minQuantity;

    @Column(name = "discounted_price", nullable = false)
    private Double discountedPrice;

    public BulkPricing() {
    }

    public BulkPricing(Long bulkId, Product product, Integer minQuantity, Double discountedPrice) {
        this.bulkId = bulkId;
        this.product = product;
        this.minQuantity = minQuantity;
        this.discountedPrice = discountedPrice;
    }

    public Long getBulkId() {
        return bulkId;
    }

    public void setBulkId(Long bulkId) {
        this.bulkId = bulkId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
