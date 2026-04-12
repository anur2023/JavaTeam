package com.doctor.appoint.modules.appointment.controller;

import com.doctor.appoint.modules.appointment.dto.SlotRequest;
import com.doctor.appoint.modules.appointment.dto.SlotResponse;
import com.doctor.appoint.modules.appointment.service.SlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    // Only doctors/admins can create slots
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<SlotResponse> createSlot(@RequestBody SlotRequest request) {
        return ResponseEntity.ok(slotService.createSlot(request));
    }

    // Anyone authenticated can view slots for a doctor
    @GetMapping("/{doctorId}")
    public ResponseEntity<List<SlotResponse>> getSlots(@PathVariable Long doctorId) {
        return ResponseEntity.ok(slotService.getSlotsByDoctor(doctorId));
    }

    // Only available (unbooked) slots
    @GetMapping("/{doctorId}/available")
    public ResponseEntity<List<SlotResponse>> getAvailableSlots(@PathVariable Long doctorId) {
        return ResponseEntity.ok(slotService.getAvailableSlotsByDoctor(doctorId));
    }
}