package com.example.OnlineStationary.module.payment.service;

import com.example.OnlineStationary.module.payment.dto.PaymentDTO;
import com.example.OnlineStationary.module.payment.entity.Payment;
import com.example.OnlineStationary.module.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Create Payment
    public Payment createPayment(PaymentDTO paymentDTO) {

        Payment payment = new Payment();

        payment.setOrderId(paymentDTO.getOrderId());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());

        // Generate unique transaction ID if not provided
        if (paymentDTO.getTransactionId() == null || paymentDTO.getTransactionId().isEmpty()) {
            payment.setTransactionId(UUID.randomUUID().toString());
        } else {
            payment.setTransactionId(paymentDTO.getTransactionId());
        }

        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // Get Payment by Order ID
    public Payment getPaymentByOrderId(Long orderId) {
        Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
        return payment.orElse(null);
    }

    // Get Payment by Transaction ID
    public Payment getPaymentByTransactionId(String transactionId) {
        Optional<Payment> payment = paymentRepository.findByTransactionId(transactionId);
        return payment.orElse(null);
    }
}