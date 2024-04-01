package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public interface SlotRepository extends JpaRepository<Slot, Integer> {
    @Query(value = "SELECT * FROM doctor_appointment_system.slot WHERE id NOT IN ( SELECT slot_id FROM doctor_appointment_system.appointment WHERE (date = :date) AND (doctor_id = :doctorId));", nativeQuery = true)
    List<Slot> findFreeSlotsByDateAndDoctorId(@Param("date") LocalDate date, @Param("doctorId") int doctorId);

}
