package com.ait_31_2.doctor_appointment_app.domain.dto;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO class representing an appointment.
 * Contains information about the appointment, including ID, patient ID, doctor ID, slot ID, date, and visit completion status.
 *
 * @author Tetiana
 * @version 1.0.0
 */
@Schema(description = "DTO class representing an appointment")
public class AppointmentDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "The ID of the appointment")
    private int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "The ID of the patient")
    private UserDto patientId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "The ID of the doctor")
    private UserDto doctorId;
    @Schema(description = "The ID of the slot")
    private Slot slotId;
    @Schema(description = "The date of the appointment")
    private LocalDate date;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Indicates if the visit is complete or not")
    private Boolean visitComplete;

    /**
     * Default constructor for AppointmentDto.
     */
    public AppointmentDto() {
    }

    /**
     * Constructs a new AppointmentDto object with the specified parameters.
     *
     * @param id            the ID of the appointment
     * @param patientId     the ID of the patient
     * @param doctorId      the ID of the doctor
     * @param slotId        the slot ID
     * @param date          the date of the appointment
     * @param visitComplete the visit completion status
     */
    public AppointmentDto(int id, UserDto patientId, UserDto doctorId, Slot slotId, LocalDate date, Boolean visitComplete) {
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

    public void setId(int id) {
        this.id = id;
    }

    public UserDto getPatientId() {
        return patientId;
    }

    public void setPatientId(UserDto patientId) {
        this.patientId = patientId;
    }

    public UserDto getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UserDto doctorId) {
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

    public Boolean getVisitComplete() {
        return visitComplete;
    }

    public void setVisitComplete(Boolean visitComplete) {
        this.visitComplete = visitComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentDto that)) return false;
        return getId() == that.getId() && Objects.equals(getPatientId(), that.getPatientId()) && Objects.equals(getDoctorId(), that.getDoctorId()) && Objects.equals(getSlotId(), that.getSlotId()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getVisitComplete(), that.getVisitComplete());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPatientId(), getDoctorId(), getSlotId(), getDate(), getVisitComplete());
    }

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", slot=" + slotId +
                ", date=" + date +
                ", visitComplete=" + visitComplete +
                '}';
    }
}
