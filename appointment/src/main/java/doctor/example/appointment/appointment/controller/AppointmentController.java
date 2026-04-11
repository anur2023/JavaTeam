package doctor.example.appointment.appointment.controller;

import doctor.example.appointment.appointment.entity.Appointment;
import doctor.example.appointment.appointment.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public Appointment book(@RequestParam Long patientId,
                            @RequestParam Long doctorId,
                            @RequestParam Long slotId,
                            @RequestParam String mode) {
        return appointmentService.bookAppointment(patientId, doctorId, slotId, mode);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable Long patientId) {
        return appointmentService.getByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getByDoctor(doctorId);
    }

    @PutMapping("/{id}")
    public Appointment updateStatus(@PathVariable Long id,
                                    @RequestParam String status) {
        return appointmentService.updateStatus(id, status);
    }
}