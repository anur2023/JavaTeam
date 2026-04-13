package com.example.OnlineStationary.module.discount.controller;

import com.example.OnlineStationary.module.discount.dto.*;
import com.example.OnlineStationary.module.discount.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    // POST /api/discounts  → Admin creates a discount/coupon
    @PostMapping
    public ResponseEntity<DiscountResponse> createDiscount(@RequestBody DiscountRequest request) {
        return ResponseEntity.ok(discountService.createDiscount(request));
    }

    // GET /api/discounts  → Admin views all discounts
    @GetMapping
    public ResponseEntity<List<DiscountResponse>> getAllDiscounts() {
        return ResponseEntity.ok(discountService.getAllDiscounts());
    }

    // GET /api/discounts/{id}  → Get discount by ID
    @GetMapping("/{id}")
    public ResponseEntity<DiscountResponse> getDiscountById(@PathVariable Long id) {
        return ResponseEntity.ok(discountService.getDiscountById(id));
    }

    // DELETE /api/discounts/{id}  → Admin deletes a discount
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.ok("Discount deleted successfully");
    }

    // POST /api/discounts/validate  → Customer validates a coupon before checkout
    @PostMapping("/validate")
    public ResponseEntity<CouponValidateResponse> validateCoupon(
            @RequestBody CouponValidateRequest request) {
        return ResponseEntity.ok(discountService.validateCoupon(request));
    }
}