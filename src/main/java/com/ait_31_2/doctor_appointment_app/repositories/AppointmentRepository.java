package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing appointments.
 * <p>
 * Provides methods to interact with the database for appointment-related operations.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    /**
     * Retrieves all appointments for a specific patient within a specified date interval.
     *
     * @param patientId the ID of the patient
     * @param timeStart the start date of the interval
     * @param timeEnd   the end date of the interval
     * @return a list of appointments for the specified patient within the date interval
     */
    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM appointment " +
            "JOIN  slot ON appointment.slot_id = slot.id JOIN " +
            "user ON appointment.doctor_id = user.id WHERE (  appointment.patient_id = :patientId)" +
            " AND appointment.date BETWEEN (:timeStart) AND (:timeEnd) ORDER BY appointment.date;", nativeQuery = true)
    List<Appointment> findAllAppointmentsPatientByDataInterval(
            @Param("patientId") int patientId,
            @Param("timeStart") LocalDate timeStart,
            @Param("timeEnd") LocalDate timeEnd);

    /**
     * Retrieves all appointments for a specific doctor within a specified date interval.
     *
     * @param doctorId  the ID of the doctor
     * @param timeStart the start date of the interval
     * @param timeEnd   the end date of the interval
     * @return a list of appointments for the specified doctor within the date interval
     */
    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM appointment " +
            "JOIN  slot ON appointment.slot_id = slot.id JOIN " +
            "user ON appointment.patient_id = user.id WHERE (  appointment.doctor_id = :doctorId)" +
            " AND appointment.date BETWEEN (:timeStart) AND (:timeEnd) ORDER BY appointment.date;", nativeQuery = true)
    List<Appointment> findAllAppointmentsDoctorByDataInterval(
            @Param("doctorId") int doctorId,
            @Param("timeStart") LocalDate timeStart,
            @Param("timeEnd") LocalDate timeEnd);

    /**
     * Retrieves future appointments for a specific patient.
     *
     * @param patientId the ID of the patient
     * @return a list of future appointments for the specified patient
     */
    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM appointment" +
            "        JOIN  slot ON appointment.slot_id = slot.id JOIN " +
            "        user ON appointment.doctor_id = user.id WHERE (  appointment.patient_id =:patientId)" +
            "        AND (appointment.date > CURRENT_DATE()  OR (appointment.date = CURRENT_DATE() AND slot.time_end >= CURRENT_TIME()))" +
            "        order by appointment.date asc limit 2;", nativeQuery = true)
    List<Appointment> findFutureAppointmentsPatient(@Param("patientId") int patientId);

    /**
     * Retrieves future appointments for a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @return a list of future appointments for the specified doctor
     */
    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM appointment" +
            "        JOIN  slot ON appointment.slot_id = slot.id JOIN " +
            "        user ON appointment.patient_id = user.id WHERE (  appointment.doctor_id =:doctorId)" +
            "        AND (appointment.date > CURRENT_DATE()  OR (appointment.date = CURRENT_DATE() AND slot.time_end >= CURRENT_TIME()))" +
            "        order by appointment.date asc limit 4;", nativeQuery = true)
    List<Appointment> findFutureAppointmentsDoctor(@Param("doctorId") int doctorId);

    /**
     * Retrieves past appointments for a specific patient.
     *
     * @param patientId the ID of the patient
     * @return a list of past appointments for the specified patient
     */
    @Query(value = "SELECT  appointment.*, slot.time_start, slot.time_end, user.name, user.surname FROM appointment" +
            "        JOIN  slot ON appointment.slot_id = slot.id JOIN " +
            "        user ON appointment.doctor_id = user.id WHERE (  appointment.patient_id =:patientId)" +
            "        AND (appointment.date < CURRENT_DATE() OR (appointment.date = CURRENT_DATE() AND slot.time_end < CURRENT_TIME()))" +
            "        order by appointment.date DESC limit 2;", nativeQuery = true)
    List<Appointment> findPastAppointments(@Param("patientId") int patientId);


}
