package doctor.example.appointment.availability.repository;

import doctor.example.appointment.availability.entity.Available;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailableRepository extends JpaRepository<Available, Integer> {

    List<Available> findByDocId(int docId);

    List<Available> findByDocIdAndDate(int docId, LocalDate date);
}
