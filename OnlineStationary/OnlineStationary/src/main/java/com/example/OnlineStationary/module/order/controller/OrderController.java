package com.example.OnlineStationary.module.order.controller;

import com.example.OnlineStationary.module.order.dto.OrderResponse;
import com.example.OnlineStationary.module.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST /api/orders/place?userId=1&couponCode=SAVE10
    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestParam Long userId,
                                                    @RequestParam(required = false) String couponCode) {
        return ResponseEntity.ok(orderService.placeOrder(userId, couponCode));
    }

    // GET /api/orders/user/1
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // GET /api/orders/1
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // PUT /api/orders/1/status?status=SHIPPED
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId,
                                                           @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}