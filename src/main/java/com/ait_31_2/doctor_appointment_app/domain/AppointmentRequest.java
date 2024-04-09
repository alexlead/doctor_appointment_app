package com.ait_31_2.doctor_appointment_app.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Form for creating new appointment or modification of existing.
 *
 * Represents the data required to create a new appointment or modify an existing one,
 *   including appointment ID, date, patient or doctor ID, slot ID.
 *
 */
@Schema(
        description = "Create new/update appointment form"
)
public class AppointmentRequest {
    @Schema(
            description = "Appointment id. If id = 0: create a new appointment, otherwise - edit the existing one",
            example = "0")
    private int appointmentId;
    @Schema(
            description = "Date",
            example = "2024-04-15")
    private LocalDate date;
    @Schema(
            description = "Patient or Doctor id",
            example = "2")
    private int userId1;
    @Schema(
            description = "Slot id",
            example = "3")
    private int slotId;

    public AppointmentRequest(int appointmentId, LocalDate date, int userId1, int slotId) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.userId1 = userId1;
        this.slotId = slotId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getUserId1() {
        return userId1;
    }

    public int getSlotId() {
        return slotId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentRequest that)) return false;
        return getAppointmentId() == that.getAppointmentId() && getUserId1() == that.getUserId1() && getSlotId() == that.getSlotId() && Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppointmentId(), getDate(), getUserId1(), getSlotId());
    }

    @Override
    public String toString() {
        return "NewAppointmentRequest{" +
                "appointmentId=" + appointmentId +
                ", date=" + date +
                ", userId1=" + userId1 +
                ", slotId=" + slotId +
                '}';
    }
}
