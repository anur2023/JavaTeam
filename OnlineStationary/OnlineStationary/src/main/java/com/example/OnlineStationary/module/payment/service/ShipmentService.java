package com.example.OnlineStationary.module.payment.service;

import com.example.OnlineStationary.module.payment.dto.ShipmentDTO;
import com.example.OnlineStationary.module.payment.entity.Shipment;
import com.example.OnlineStationary.module.payment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    // Create Shipment
    public Shipment createShipment(ShipmentDTO shipmentDTO) {

        Shipment shipment = new Shipment();

        shipment.setOrderId(shipmentDTO.getOrderId());
        shipment.setStatus(shipmentDTO.getStatus());

        // Generate tracking number if not provided
        if (shipmentDTO.getTrackingNumber() == null || shipmentDTO.getTrackingNumber().isEmpty()) {
            shipment.setTrackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8));
        } else {
            shipment.setTrackingNumber(shipmentDTO.getTrackingNumber());
        }

        shipment.setEstimatedDelivery(shipmentDTO.getEstimatedDelivery());
        shipment.setCreatedAt(LocalDateTime.now());

        return shipmentRepository.save(shipment);
    }

    // Get Shipment by Order ID
    public Shipment getShipmentByOrderId(Long orderId) {
        Optional<Shipment> shipment = shipmentRepository.findByOrderId(orderId);
        return shipment.orElse(null);
    }

    // Get Shipment by Tracking Number
    public Shipment getShipmentByTrackingNumber(String trackingNumber) {
        Optional<Shipment> shipment = shipmentRepository.findByTrackingNumber(trackingNumber);
        return shipment.orElse(null);
    }
}