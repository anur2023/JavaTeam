package com.ecommerce.electronic.module.order.controller;

import com.ecommerce.electronic.module.order.dto.PaymentDto;
import com.ecommerce.electronic.module.order.entity.Payment;
import com.ecommerce.electronic.module.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment processPayment(@RequestBody PaymentDto dto) {
        return paymentService.processPayment(dto);
    }

    @GetMapping("/order/{orderId}")
    public Payment getPaymentByOrderId(@PathVariable Long orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}