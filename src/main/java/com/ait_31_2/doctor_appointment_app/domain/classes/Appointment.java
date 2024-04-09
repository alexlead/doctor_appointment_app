package com.ait_31_2.doctor_appointment_app.domain.classes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;
/**
 * Entity class representing an appointment.
 * Contains information about a specific appointment, including ID, patient ID, doctor ID, slot ID, date, and visit completion status.
 *
 * @author Arman
 * @version 1.0.0
 */
@Entity
@Table(name = "appointment")
@Schema(description = "Entity class representing an appointment")
public class Appointment  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "The ID of the appointment")
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "patient_id", nullable = false)
    @Schema(description = "The ID of the patient")
    private User patientId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    @Schema(description = "The ID of the doctor")
    private User doctorId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "slot_id", nullable = false)
    @Schema(description = "The ID of the slot")
    private Slot slotId;
    @NotNull
    @Column(name = "date")
    @Schema(description = "The date of the appointment")
    private LocalDate date;
    @NotNull
    @Column(name = "visit_complete")
    @Schema(description = "Indicates if the visit is complete or not")
    private boolean visitComplete;

    public Appointment() {
    }
    /**
     * Constructs a new Appointment object with the specified parameters.
     *
     * @param id            the ID of the appointment
     * @param patientId     the ID of the patient
     * @param doctorId      the ID of the doctor
     * @param slotId        the ID of the slot
     * @param date          the date of the appointment
     * @param visitComplete the visit completion status
     */
    public Appointment(int id, User patientId, User doctorId, Slot slotId, LocalDate date, boolean visitComplete) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.date =date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
