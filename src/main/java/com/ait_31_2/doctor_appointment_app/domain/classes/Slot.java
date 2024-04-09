package com.ait_31_2.doctor_appointment_app.domain.classes;

import com.ait_31_2.doctor_appointment_app.domain.interfaces.SlotInterface;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.sql.Time;
import java.util.Objects;

/**
 * Entity class representing a time slot.
 * Contains information about a specific time slot, including ID, start time, and end time.
 *
 * @author Arman
 * @version 1.0.0
 */
@Entity
@Table(name = "slot")
@Schema(description = "Entity class representing a time slot.")
public class Slot implements SlotInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "The ID of the slot")
    private int id;

    @NotNull
    @Column(name = "time_start")
    @Schema(description = "The start time of the time slot")
    private Time startTime;

    @NotNull
    @Column(name = "time_end")
    @Schema(description = "The end time of the time slot")
    private Time endTime;

    public Slot() {
    }
    /**
     * Constructs a new Slot object with the specified parameters.
     *
     * @param id        the ID of the time slot
     * @param startTime the start time of the time slot
     * @param endTime   the end time of the time slot
     */
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
