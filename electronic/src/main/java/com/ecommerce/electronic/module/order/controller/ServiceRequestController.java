package com.ecommerce.electronic.module.order.controller;

import com.ecommerce.electronic.module.order.dto.ServiceRequestDto;
import com.ecommerce.electronic.module.order.entity.ServiceRequest;
import com.ecommerce.electronic.module.order.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-request")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping
    public ServiceRequest createServiceRequest(@RequestBody ServiceRequestDto dto) {
        return serviceRequestService.createServiceRequest(dto);
    }

    @PutMapping("/status/{requestId}")
    public ServiceRequest updateServiceRequestStatus(@PathVariable Long requestId,
                                                     @RequestParam String status) {
        return serviceRequestService.updateServiceRequestStatus(requestId, status);
    }

    @GetMapping("/user/{userId}")
    public List<ServiceRequest> getRequestsByUserId(@PathVariable Long userId) {
        return serviceRequestService.getRequestsByUserId(userId);
    }

    @GetMapping("/warranty/{warrantyId}")
    public List<ServiceRequest> getRequestsByWarrantyId(@PathVariable Long warrantyId) {
        return serviceRequestService.getRequestsByWarrantyId(warrantyId);
    }

    @GetMapping("/{id}")
    public ServiceRequest getRequestById(@PathVariable Long id) {
        return serviceRequestService.getRequestById(id);
    }

    @GetMapping
    public List<ServiceRequest> getAllRequests() {
        return serviceRequestService.getAllRequests();
    }
}