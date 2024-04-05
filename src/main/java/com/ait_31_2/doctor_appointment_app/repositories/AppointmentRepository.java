package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM doctor_appointment_system.appointment " +
            "JOIN  doctor_appointment_system.slot ON appointment.slot_id = slot.id JOIN " +
            "doctor_appointment_system.user ON appointment.doctor_id = user.id WHERE (  appointment.patient_id = :patientId)" +
            " AND appointment.date BETWEEN (:timeStart) AND (:timeEnd) ORDER BY appointment.date;", nativeQuery = true)
    List<Appointment> findAllAppointmentsPatientByDataInterval(
            @Param("patientId") int patientId,
            @Param("timeStart") LocalDate timeStart,
            @Param("timeEnd") LocalDate timeEnd);

    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM doctor_appointment_system.appointment " +
            "JOIN  doctor_appointment_system.slot ON appointment.slot_id = slot.id JOIN " +
            "doctor_appointment_system.user ON appointment.patient_id = user.id WHERE (  appointment.doctor_id = :doctorId)" +
            " AND appointment.date BETWEEN (:timeStart) AND (:timeEnd) ORDER BY appointment.date;", nativeQuery = true)
    List<Appointment> findAllAppointmentsDoctorByDataInterval(
            @Param("doctorId") int doctorId,
            @Param("timeStart") LocalDate timeStart,
            @Param("timeEnd") LocalDate timeEnd);


    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM doctor_appointment_system.appointment" +
            "        JOIN  doctor_appointment_system.slot ON appointment.slot_id = slot.id JOIN " +
            "        doctor_appointment_system.user ON appointment.doctor_id = user.id WHERE (  appointment.patient_id =:patientId)" +
            "        AND (appointment.date > CURRENT_DATE())  OR (appointment.date = CURRENT_DATE() AND slot.time_end >= CURRENT_TIME())" +
            "        order by appointment.date asc limit 2;", nativeQuery = true)
    List<Appointment> findFutureAppointmentsPatient(@Param("patientId") int patientId);


    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM doctor_appointment_system.appointment" +
            "        JOIN  doctor_appointment_system.slot ON appointment.slot_id = slot.id JOIN " +
            "        doctor_appointment_system.user ON appointment.patient_id = user.id WHERE (  appointment.doctor_id =:doctorId)" +
            "        AND (appointment.date > CURRENT_DATE())  OR (appointment.date = CURRENT_DATE() AND slot.time_end >= CURRENT_TIME())" +
            "        order by appointment.date asc limit 4;", nativeQuery = true)
    List<Appointment> findFutureAppointmentsDoctor(@Param("doctorId") int patientId);

    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM doctor_appointment_system.appointment" +
            "        JOIN  doctor_appointment_system.slot ON appointment.slot_id = slot.id JOIN " +
            "        doctor_appointment_system.user ON appointment.doctor_id = user.id WHERE (  appointment.patient_id =:patientId)" +
            "        AND (appointment.date < CURRENT_DATE()) OR (appointment.date = CURRENT_DATE() AND slot.time_end < CURRENT_TIME())" +
            "        order by appointment.date desc limit 2;", nativeQuery = true)
    List<Appointment> findPastAppointments(@Param("patientId") int patientId);


}
