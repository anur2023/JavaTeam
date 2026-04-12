package com.doctor.appoint.modules.appointment.repository;

import com.doctor.appoint.modules.appointment.entity.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {

    // All slots for a doctor
    List<AvailabilitySlot> findByDoctorId(Long doctorId);

    // Only available (unbooked) slots for a doctor
    List<AvailabilitySlot> findByDoctorIdAndIsBooked(Long doctorId, boolean isBooked);
}