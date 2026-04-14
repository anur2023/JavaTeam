package com.ecommerce.electronic.module.order.service;

import com.ecommerce.electronic.module.order.dto.ServiceRequestDto;
import com.ecommerce.electronic.module.order.entity.ServiceRequest;
import com.ecommerce.electronic.module.order.entity.Warranty;
import com.ecommerce.electronic.module.order.repository.ServiceRequestRepository;
import com.ecommerce.electronic.module.order.repository.WarrantyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private WarrantyRepository warrantyRepository;

    public ServiceRequest createServiceRequest(ServiceRequestDto dto) {

        Warranty warranty = warrantyRepository.findById(dto.getWarrantyId()).orElse(null);

        if (warranty == null) {
            throw new RuntimeException("Warranty not found");
        }

        if (!warranty.getStatus().equals("ACTIVE")) {
            throw new RuntimeException("Warranty expired or inactive");
        }

        ServiceRequest request = new ServiceRequest();
        request.setUserId(dto.getUserId());
        request.setWarranty(warranty);
        request.setIssueDescription(dto.getIssueDescription());
        request.setStatus("REQUESTED");

        return serviceRequestRepository.save(request);
    }

    public ServiceRequest updateServiceRequestStatus(Long requestId, String status) {

        ServiceRequest request = serviceRequestRepository.findById(requestId).orElse(null);

        if (request == null) {
            return null;
        }

        request.setStatus(status);

        return serviceRequestRepository.save(request);
    }

    public List<ServiceRequest> getRequestsByUserId(Long userId) {
        return serviceRequestRepository.findByUserId(userId);
    }

    public List<ServiceRequest> getRequestsByWarrantyId(Long warrantyId) {
        return serviceRequestRepository.findByWarrantyId(warrantyId);
    }

    public List<ServiceRequest> getAllRequests() {
        return serviceRequestRepository.findAll();
    }

    public ServiceRequest getRequestById(Long id) {
        return serviceRequestRepository.findById(id).orElse(null);
    }
}