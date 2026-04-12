package com.doctor.appoint.modules.appointment.service;

import com.doctor.appoint.modules.appointment.dto.SlotRequest;
import com.doctor.appoint.modules.appointment.dto.SlotResponse;
import com.doctor.appoint.modules.appointment.entity.AvailabilitySlot;
import com.doctor.appoint.modules.appointment.repository.AvailabilitySlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotService {

    private final AvailabilitySlotRepository slotRepository;

    public SlotService(AvailabilitySlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public SlotResponse createSlot(SlotRequest request) {
        AvailabilitySlot slot = new AvailabilitySlot();
        slot.setDoctorId(request.getDoctorId());
        slot.setSlotDate(request.getSlotDate());
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setBooked(false);

        AvailabilitySlot saved = slotRepository.save(slot);
        return mapToResponse(saved);
    }

    public List<SlotResponse> getSlotsByDoctor(Long doctorId) {
        return slotRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SlotResponse> getAvailableSlotsByDoctor(Long doctorId) {
        return slotRepository.findByDoctorIdAndIsBooked(doctorId, false)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SlotResponse mapToResponse(AvailabilitySlot slot) {
        return new SlotResponse(
                slot.getId(),
                slot.getDoctorId(),
                slot.getSlotDate(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.isBooked()
        );
    }
}