package com.example.OnlineStationary.module.discount.repository;

import com.example.OnlineStationary.module.discount.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByCouponCodeAndValidFromLessThanEqualAndValidToGreaterThanEqual(
            String couponCode, LocalDate today1, LocalDate today2);
}