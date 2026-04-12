package doctor.example.appointment.availability.controller;

import doctor.example.appointment.availability.entity.Available;
import doctor.example.appointment.availability.service.AvailableService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class AvailableController {

    private final AvailableService availableService;

    public AvailableController(AvailableService availableService) {
        this.availableService = availableService;
    }

    @PostMapping
    public Available createSlot(@RequestBody Available slot) {
        return availableService.addSlot(slot);
    }

    @GetMapping("/doctor/{docId}")
    public List<Available> getSlotsByDoctor(@PathVariable int docId) {
        return availableService.getSlotsByDoctor(docId);
    }

    @GetMapping("/doctor/{docId}/date/{date}")
    public List<Available> getSlotsByDoctorAndDate(
            @PathVariable int docId,
            @PathVariable LocalDate date) {
        return availableService.getSlotsByDoctorAndDate(docId, date);
    }

    @PutMapping("/{slotId}")
    public Available updateSlot(@PathVariable int slotId, @RequestBody Available slot) {
        return availableService.updateSlot(slotId, slot);
    }

    @DeleteMapping("/{slotId}")
    public void deleteSlot(@PathVariable int slotId) {
        availableService.deleteSlot(slotId);
    }
}