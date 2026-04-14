package com.ecommerce.electronic.module.order.service;

import com.ecommerce.electronic.module.order.dto.PaymentDto;
import com.ecommerce.electronic.module.order.entity.Order;
import com.ecommerce.electronic.module.order.entity.Payment;
import com.ecommerce.electronic.module.order.repository.OrderRepository;
import com.ecommerce.electronic.module.order.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Payment processPayment(PaymentDto dto) {

        Order order = orderRepository.findById(dto.getOrderId()).orElse(null);

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(dto.getAmount());
        payment.setStatus(dto.getStatus());

        if (dto.getTransactionId() == null || dto.getTransactionId().isEmpty()) {
            payment.setTransactionId(UUID.randomUUID().toString());
        } else {
            payment.setTransactionId(dto.getTransactionId());
        }

        Payment savedPayment = paymentRepository.save(payment);

        if (savedPayment.getStatus().equals("SUCCESS")) {
            order.setStatus("CONFIRMED");
        } else if (savedPayment.getStatus().equals("FAILED")) {
            order.setStatus("CANCELLED");
        }

        orderRepository.save(order);

        return savedPayment;
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId).orElse(null);
    }
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
    public java.util.List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}