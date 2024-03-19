package com.ait_31_2.doctor_appointment_app.domain.classes;

import com.ait_31_2.doctor_appointment_app.domain.interfaces.AppointmentInterface;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "appointment")
public class Appointment implements AppointmentInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user", nullable = false)
    private User patientId;


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User doctorId;


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slotId;


    @NotNull
    @Column(name = "date")
    private Date date;


    @NotNull
    @Column(name = "visit_complete")
    private boolean visitComplete;

    public Appointment() {
    }

    public Appointment(int id, User patientId, User doctorId, Slot slotId, Date date, boolean visitComplete) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.date = date;
        this.visitComplete = visitComplete;
    }

    public int getId() {
        return id;
    }


    public User getPatientId() {
        return patientId;
    }

    public void setPatientId(User patientId) {
        this.patientId = patientId;
    }

    public User getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(User doctorId) {
        this.doctorId = doctorId;
    }

    public Slot getSlotId() {
        return slotId;
    }

    public void setSlotId(Slot slotId) {
        this.slotId = slotId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isVisitComplete() {
        return visitComplete;
    }

    public void setVisitComplete(boolean visitComplete) {
        this.visitComplete = visitComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id && visitComplete == that.visitComplete && Objects.equals(patientId, that.patientId) && Objects.equals(doctorId, that.doctorId) && Objects.equals(slotId, that.slotId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, doctorId, slotId, date, visitComplete);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", slotId=" + slotId +
                ", date=" + date +
                ", visitComplete=" + visitComplete +
                '}';
    }
}
