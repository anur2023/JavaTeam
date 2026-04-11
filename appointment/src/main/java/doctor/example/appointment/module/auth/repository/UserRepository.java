package doctor.example.appointment.module.auth.repository;

import doctor.example.appointment.module.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {


    Optional<Users> findByEmail(String email);

}
