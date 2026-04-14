package com.ecommerce.electronic.module.order.repository;

import com.ecommerce.electronic.module.order.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByUserId(Long userId);

    List<ServiceRequest> findByWarrantyId(Long warrantyId);
}