package com.ecommerce.electronic.module.order.controller;

import com.ecommerce.electronic.module.order.entity.Warranty;
import com.ecommerce.electronic.module.order.service.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warranties")
public class WarrantyController {

    @Autowired
    private WarrantyService warrantyService;

    @GetMapping("/user/{userId}")
    public List<Warranty> getWarrantiesByUserId(@PathVariable Long userId) {
        return warrantyService.getWarrantiesByUserId(userId);
    }

    @GetMapping("/{id}")
    public Warranty getWarrantyById(@PathVariable Long id) {
        return warrantyService.getWarrantyById(id);
    }

    @GetMapping("/order-item/{orderItemId}")
    public List<Warranty> getWarrantyByOrderItemId(@PathVariable Long orderItemId) {
        return warrantyService.getWarrantyByOrderItemId(orderItemId);
    }

    @PutMapping("/status/{warrantyId}")
    public Warranty updateWarrantyStatus(@PathVariable Long warrantyId,
                                         @RequestParam String status) {
        return warrantyService.updateWarrantyStatus(warrantyId, status);
    }

    @GetMapping
    public List<Warranty> getAllWarranties() {
        return warrantyService.getAllWarranties();
    }
}