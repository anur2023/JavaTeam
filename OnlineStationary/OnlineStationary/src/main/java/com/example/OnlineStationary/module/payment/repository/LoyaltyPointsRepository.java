package com.example.OnlineStationary.module.payment.repository;

import com.example.OnlineStationary.module.payment.entity.LoyaltyPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {

    Optional<LoyaltyPoints> findByUserId(Long userId);
}