package com.ait_31_2.doctor_appointment_app.domain.classes;

import com.ait_31_2.doctor_appointment_app.domain.interfaces.SlotInterface;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.sql.Time;
import java.util.Objects;


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

    public Slot() {
    }

    public Slot(int id, Time startTime, Time endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Override
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slot slot)) return false;
        return getId() == slot.getId() && Objects.equals(getStartTime(), slot.getStartTime()) && Objects.equals(getEndTime(), slot.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartTime(), getEndTime());
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
