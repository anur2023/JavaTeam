package doctor.example.appointment.Doctor.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doctorId;

    private long userId;

    private int onlineFee;
    private int offlineFee;

    private int experienceYears;

    public Doctor(long doctorId, long userId, int experienceYears, int onlineFee, int offlineFee) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.experienceYears = experienceYears;
        this.onlineFee = onlineFee;
        this.offlineFee = offlineFee;
    }

    public Doctor() {
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public int getOnlineFee() {
        return onlineFee;
    }

    public void setOnlineFee(int onlineFee) {
        this.onlineFee = onlineFee;
    }

    public int getOfflineFee() {
        return offlineFee;
    }

    public void setOfflineFee(int offlineFee) {
        this.offlineFee = offlineFee;
    }
}