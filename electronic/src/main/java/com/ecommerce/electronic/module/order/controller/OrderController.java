package com.ecommerce.electronic.module.order.controller;

import com.ecommerce.electronic.module.order.dto.OrderRequestDto;
import com.ecommerce.electronic.module.order.entity.Order;
import com.ecommerce.electronic.module.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order placeOrder(@RequestBody OrderRequestDto dto) {
        return orderService.placeOrder(dto);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PutMapping("/cancel/{orderId}")
    public Order cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @PutMapping("/status/{orderId}")
    public Order updateOrderStatus(@PathVariable Long orderId,
                                   @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}