package com.example.OnlineStationary.module.payment.controller;

import com.example.OnlineStationary.module.payment.dto.ShipmentDTO;
import com.example.OnlineStationary.module.payment.entity.Shipment;
import com.example.OnlineStationary.module.payment.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    // Create Shipment
    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody ShipmentDTO shipmentDTO) {
        Shipment shipment = shipmentService.createShipment(shipmentDTO);
        return ResponseEntity.ok(shipment);
    }

    // Get Shipment by Order ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Shipment> getShipmentByOrderId(@PathVariable Long orderId) {
        Shipment shipment = shipmentService.getShipmentByOrderId(orderId);
        if (shipment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shipment);
    }

    // Get Shipment by Tracking Number
    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<Shipment> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        Shipment shipment = shipmentService.getShipmentByTrackingNumber(trackingNumber);
        if (shipment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shipment);
    }
}