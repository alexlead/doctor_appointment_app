package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT  slot.*  FROM slot LEFT JOIN appointment ON slot.id = appointment.slot_id AND (appointment.doctor_id = :doctorId) AND (appointment.date = :date) WHERE appointment.slot_id IS NULL;", nativeQuery = true)
    List<Slot> findFreeSlotsByDateAndDoctor(@Param("date") Date date, @Param("doctorId") int doctorId);


}
