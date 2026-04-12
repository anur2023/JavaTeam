package doctor.example.appointment.availability.service;

import doctor.example.appointment.availability.entity.Available;
import doctor.example.appointment.availability.repository.AvailableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailableService {

    private final AvailableRepository availableRepository;

    public AvailableService(AvailableRepository availableRepository) {
        this.availableRepository = availableRepository;
    }

    public Available addSlot(Available slot) {
        return availableRepository.save(slot);
    }

    public List<Available> getSlotsByDoctor(int docId) {
        return availableRepository.findByDocId(docId);
    }

    public List<Available> getSlotsByDoctorAndDate(int docId, LocalDate date) {
        return availableRepository.findByDocIdAndDate(docId, date);
    }

    public Available updateSlot(int slotId, Available updatedSlot) {
        Optional<Available> existingSlot = availableRepository.findById(slotId);

        if (existingSlot.isPresent()) {
            Available slot = existingSlot.get();

            slot.setDate(updatedSlot.getDate());
            slot.setStartTime(updatedSlot.getStartTime());
            slot.setEndTime(updatedSlot.getEndTime());
            slot.setMode(updatedSlot.getMode());
            slot.setSlotAvail(updatedSlot.isSlotAvail());

            return availableRepository.save(slot);
        } else {
            return null;
        }
    }

    public void deleteSlot(int slotId) {
        availableRepository.deleteById(slotId);
    }
}
