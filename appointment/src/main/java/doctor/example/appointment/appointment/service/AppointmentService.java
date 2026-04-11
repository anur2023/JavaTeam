package doctor.example.appointment.appointment.service;

import doctor.example.appointment.appointment.entity.Appointment;
import doctor.example.appointment.appointment.repository.AppointmentRepository;
import doctor.example.appointment.availability.entity.Available;
import doctor.example.appointment.availability.repository.AvailableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailableRepository availableRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              AvailableRepository availableRepository) {
        this.appointmentRepository = appointmentRepository;
        this.availableRepository = availableRepository;
    }

    public Appointment bookAppointment(Long patientId, Long doctorId, Long slotId, String mode) {

        Available slot = availableRepository.findById(slotId.intValue())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (!slot.isSlotAvail()) {
            throw new RuntimeException("Slot already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setSlotId(slotId);
        appointment.setMode(mode);
        appointment.setStatus("SCHEDULED");

        appointment.setBookingTime(LocalDateTime.now());
        appointment.setCreatedAt(LocalDateTime.now());

        if (mode.equalsIgnoreCase("ONLINE")) {
            appointment.setConsultationLink("https://meet.link/" + slotId);
        }

        slot.setSlotAvail(false);
        availableRepository.save(slot);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public Appointment updateStatus(Long id, String status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
}