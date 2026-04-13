package doctor.example.appointment.Doctor.service;

import doctor.example.appointment.Doctor.entity.Doctor;
import doctor.example.appointment.Doctor.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public void deleteDoctor(long id) {
        doctorRepository.deleteById(id);
    }

    public Doctor updateDoctor(long id, Doctor updatedDoctor) {

        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        existingDoctor.setUserId(updatedDoctor.getUserId());
        existingDoctor.setExperienceYears(updatedDoctor.getExperienceYears());
        existingDoctor.setOnlineFee(updatedDoctor.getOnlineFee());
        existingDoctor.setOfflineFee(updatedDoctor.getOfflineFee());

        return doctorRepository.save(existingDoctor);
    }
}