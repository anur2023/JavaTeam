package com.example.OnlineStationary.module.discount.service;

import com.example.OnlineStationary.module.discount.dto.*;
import com.example.OnlineStationary.module.discount.entity.Discount;
import com.example.OnlineStationary.module.discount.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    // Create discount (Admin)
    public DiscountResponse createDiscount(DiscountRequest request) {
        Discount discount = new Discount();
        discount.setType(Discount.DiscountType.valueOf(request.getType().toUpperCase()));
        discount.setValue(request.getValue());
        discount.setCouponCode(request.getCouponCode());
        discount.setValidFrom(request.getValidFrom());
        discount.setValidTo(request.getValidTo());
        return toResponse(discountRepository.save(discount));
    }

    // Get all discounts
    public List<DiscountResponse> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get discount by ID
    public DiscountResponse getDiscountById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));
        return toResponse(discount);
    }

    // Delete discount (Admin)
    public void deleteDiscount(Long id) {
        if (!discountRepository.existsById(id)) {
            throw new RuntimeException("Discount not found");
        }
        discountRepository.deleteById(id);
    }

    // Validate and apply coupon
    public CouponValidateResponse validateCoupon(CouponValidateRequest request) {
        LocalDate today = LocalDate.now();
        Optional<Discount> discount = discountRepository
                .findByCouponCodeAndValidFromLessThanEqualAndValidToGreaterThanEqual(
                        request.getCouponCode(), today, today);

        CouponValidateResponse response = new CouponValidateResponse();
        response.setOriginalAmount(request.getTotalAmount());

        if (discount.isPresent()) {
            BigDecimal discountedAmount = request.getTotalAmount()
                    .subtract(discount.get().getValue())
                    .max(BigDecimal.ZERO);
            response.setValid(true);
            response.setMessage("Coupon applied successfully");
            response.setDiscountedAmount(discountedAmount);
            response.setSavings(request.getTotalAmount().subtract(discountedAmount));
        } else {
            response.setValid(false);
            response.setMessage("Invalid or expired coupon code");
            response.setDiscountedAmount(request.getTotalAmount());
            response.setSavings(BigDecimal.ZERO);
        }

        return response;
    }

    // Internal use by OrderService
    public BigDecimal applyCoupon(String couponCode, BigDecimal totalAmount) {
        LocalDate today = LocalDate.now();
        Optional<Discount> discount = discountRepository
                .findByCouponCodeAndValidFromLessThanEqualAndValidToGreaterThanEqual(
                        couponCode, today, today);
        return discount.map(d -> totalAmount.subtract(d.getValue()).max(BigDecimal.ZERO))
                .orElse(totalAmount);
    }

    // Entity → DTO
    private DiscountResponse toResponse(Discount discount) {
        DiscountResponse response = new DiscountResponse();
        response.setDiscountId(discount.getDiscountId());
        response.setType(discount.getType().name());
        response.setValue(discount.getValue());
        response.setCouponCode(discount.getCouponCode());
        response.setValidFrom(discount.getValidFrom());
        response.setValidTo(discount.getValidTo());
        response.setActive(
                !LocalDate.now().isBefore(discount.getValidFrom()) &&
                        !LocalDate.now().isAfter(discount.getValidTo())
        );
        return response;
    }
}