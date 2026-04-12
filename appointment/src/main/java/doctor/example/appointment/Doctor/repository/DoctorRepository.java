package doctor.example.appointment.Doctor.repository;

import java.util.List;
import doctor.example.appointment.Doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository  extends JpaRepository<Doctor,Long> {
    List<Doctor> findBySpecialization(String specialization);

    Doctor findByEmail(String email);
}
