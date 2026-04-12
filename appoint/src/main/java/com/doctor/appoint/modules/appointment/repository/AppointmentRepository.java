package com.doctor.appoint.modules.appointment.repository;

import com.doctor.appoint.modules.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // All appointments for a patient
    List<Appointment> findByPatientId(Long patientId);

    // All appointments for a doctor
    List<Appointment> findByDoctorId(Long doctorId);

    // Check if slot is already booked (double-booking guard)
    boolean existsBySlotId(Long slotId);
}