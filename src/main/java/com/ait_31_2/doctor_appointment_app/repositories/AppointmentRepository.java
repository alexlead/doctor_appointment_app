package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

@Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM doctor_appointment_system.appointment " +
        "JOIN  doctor_appointment_system.slot ON appointment.slot_id = slot.id JOIN " +
        "doctor_appointment_system.user ON appointment.doctor_id = user.id WHERE (  appointment.patient_id = :patientId)" +
        " AND appointment.date BETWEEN (:timeStart) AND (:timeEnd);", nativeQuery = true)
List<Appointment> findAllAppointmentsPatientByDataInterval(
        @Param("patientId") int patientId,
        @Param("timeStart") Date timeStart,
        @Param("timeEnd") Date timeEnd);
}
