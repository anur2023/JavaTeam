package com.ecommerce.electronic.module.order.service;

import com.ecommerce.electronic.module.order.entity.Warranty;
import com.ecommerce.electronic.module.order.repository.WarrantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WarrantyService {

    @Autowired
    private WarrantyRepository warrantyRepository;

    public List<Warranty> getWarrantiesByUserId(Long userId) {
        return warrantyRepository.findByOrderItem_Order_UserId(userId);
    }

    public Warranty getWarrantyById(Long id) {
        return warrantyRepository.findById(id).orElse(null);
    }

    public List<Warranty> getWarrantyByOrderItemId(Long orderItemId) {
        return warrantyRepository.findByOrderItemId(orderItemId);
    }

    public Warranty updateWarrantyStatus(Long warrantyId, String status) {

        Warranty warranty = warrantyRepository.findById(warrantyId).orElse(null);

        if (warranty == null) {
            return null;
        }

        warranty.setStatus(status);
        return warrantyRepository.save(warranty);
    }

    public void updateExpiredWarranties() {

        List<Warranty> warranties = warrantyRepository.findAll();

        for (Warranty warranty : warranties) {
            if (warranty.getExpiryDate().isBefore(LocalDate.now())) {
                warranty.setStatus("EXPIRED");
                warrantyRepository.save(warranty);
            }
        }
    }

    public List<Warranty> getAllWarranties() {
        return warrantyRepository.findAll();
    }
}