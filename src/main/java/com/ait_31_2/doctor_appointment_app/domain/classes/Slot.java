package com.ait_31_2.doctor_appointment_app.domain.classes;

import com.ait_31_2.doctor_appointment_app.domain.interfaces.SlotInterface;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.sql.Time;


@Entity
@Table(name = "slot")
public class Slot implements SlotInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @NotNull
    @Column(name = "time_start")
    private Time startTime;

    @NotNull
    @Column(name = "time_end")
    private Time endTime;


    public Slot(int id, Time startTime, Time endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
