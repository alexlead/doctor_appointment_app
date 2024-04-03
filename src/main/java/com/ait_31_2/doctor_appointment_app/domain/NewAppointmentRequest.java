package com.ait_31_2.doctor_appointment_app.domain;

import java.time.LocalDate;
import java.util.Objects;

public class NewAppointmentRequest {

    private LocalDate date;
    private int userId1;
    private int slotId;

    public NewAppointmentRequest(LocalDate date, int userId1, int slotId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewAppointmentRequest that)) return false;
        return getUserId1() == that.getUserId1() && getSlotId() == that.getSlotId() && Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getUserId1(), getSlotId());
    }

    @Override
    public String toString() {
        return "NewAppointmentRequest{" +
                "date=" + date +
                ", userId1=" + userId1 +
                ", slotId=" + slotId +
                '}';
    }
}
