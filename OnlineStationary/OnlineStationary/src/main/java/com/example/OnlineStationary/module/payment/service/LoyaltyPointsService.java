package com.example.OnlineStationary.module.payment.service;

import com.example.OnlineStationary.module.payment.dto.LoyaltyPointsDTO;
import com.example.OnlineStationary.module.payment.entity.LoyaltyPoints;
import com.example.OnlineStationary.module.payment.repository.LoyaltyPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoyaltyPointsService {

    @Autowired
    private LoyaltyPointsRepository loyaltyPointsRepository;

    // Add or Update Loyalty Points
    public LoyaltyPoints addOrUpdatePoints(LoyaltyPointsDTO dto) {

        Optional<LoyaltyPoints> existing = loyaltyPointsRepository.findByUserId(dto.getUserId());

        LoyaltyPoints loyaltyPoints;

        if (existing.isPresent()) {
            loyaltyPoints = existing.get();
            loyaltyPoints.setPoints(loyaltyPoints.getPoints() + dto.getPoints());
        } else {
            loyaltyPoints = new LoyaltyPoints();
            loyaltyPoints.setUserId(dto.getUserId());
            loyaltyPoints.setPoints(dto.getPoints());
        }

        return loyaltyPointsRepository.save(loyaltyPoints);
    }

    // Get Loyalty Points by User ID
    public LoyaltyPoints getPointsByUserId(Long userId) {
        Optional<LoyaltyPoints> loyaltyPoints = loyaltyPointsRepository.findByUserId(userId);
        return loyaltyPoints.orElse(null);
    }
}