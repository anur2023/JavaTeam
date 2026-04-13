package com.example.OnlineStationary.module.payment.service;

import com.example.OnlineStationary.module.payment.dto.SubscriptionDTO;
import com.example.OnlineStationary.module.payment.entity.Subscription;
import com.example.OnlineStationary.module.payment.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // Create Subscription
    public Subscription createSubscription(SubscriptionDTO dto) {

        Subscription subscription = new Subscription();

        subscription.setUserId(dto.getUserId());
        subscription.setProductId(dto.getProductId());
        subscription.setFrequency(dto.getFrequency());
        subscription.setNextDelivery(dto.getNextDelivery());
        subscription.setCreatedAt(LocalDateTime.now());

        return subscriptionRepository.save(subscription);
    }

    // Get all subscriptions of a user
    public List<Subscription> getSubscriptionsByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    // Get all subscriptions of a product
    public List<Subscription> getSubscriptionsByProductId(Long productId) {
        return subscriptionRepository.findByProductId(productId);
    }
}