package com.doctor.appoint.modules.doctor.repository;

import com.doctor.appoint.modules.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByUserId(Long userId);
}