package com.doctor.appoint.modules.appointment.repository;

import com.doctor.appoint.modules.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    // JPQL needed because slot is a OneToOne object, not a plain column
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.slot.id = :slotId")
    boolean existsBySlotId(@Param("slotId") Long slotId);
}