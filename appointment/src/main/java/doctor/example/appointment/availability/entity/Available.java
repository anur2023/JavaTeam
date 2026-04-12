package doctor.example.appointment.availability.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "available")
public class Available {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slotId;
    private int docId;
    private LocalDate date;
    private String mode;
    private boolean slotAvail;
    private LocalTime startTime;
    private LocalTime endTime;


    public Available(int slotId, int docId, LocalDate date, String mode, boolean slotAvail) {
        this.slotId = slotId;
        this.docId = docId;
        this.date = date;
        this.mode = mode;
        this.slotAvail = slotAvail;
    }

    public Available() {}

    public int getSlotId() {
        return slotId;
    }
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getDocId() {
        return docId;
    }
    public void setDocId(int docId) {
        this.docId = docId;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isSlotAvail() {
        return slotAvail;
    }
    public void setSlotAvail(boolean slotAvail) {
        this.slotAvail = slotAvail;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
