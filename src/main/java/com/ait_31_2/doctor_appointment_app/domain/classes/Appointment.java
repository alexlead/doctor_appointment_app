package com.ait_31_2.doctor_appointment_app.domain.classes;

import com.ait_31_2.doctor_appointment_app.repositories.AppointmentRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "Appointment")
public class Appointment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Id
    @NotNull
    @Column(name = "patientId")
    private int patientId;


    @Id
    @NotNull
    @Column(name = "doctorId")
    private int doctorId;


    @Id
    @NotNull
    @Column(name = "slotId")
    private int slotId;


    @NotNull
    @Column(name = "date")
    private Date date;


    @NotNull
    @Column(name = "visitComplete")
    private boolean visitComplete;

    public Appointment(int id, int patientId, int doctorId, int slotId, Date date, boolean visitComplete) {
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


    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
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
}
