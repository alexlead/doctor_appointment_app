package com.ait_31_2.doctor_appointment_app.domain.classes;

import com.ait_31_2.doctor_appointment_app.domain.interfaces.SlotInterface;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import javax.xml.crypto.Data;
import java.util.List;

@Entity
@Table(name = "slot")
public class Slot implements SlotInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @NotNull
    @Column(name = "time_start")
    private Data startTime;

    @NotNull
    @Column(name = "time_end")
    private Data endTime;


    public Slot(int id, Data startTime, Data endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int getId() {
        return id;
    }


    @Override
    public Data getStartTime() {
        return startTime;
    }

    public void setStartTime(Data startTime) {
        this.startTime = startTime;
    }

    public Data getEndTime() {
        return endTime;
    }

    public void setEndTime(Data endTime) {
        this.endTime = endTime;
    }
}
