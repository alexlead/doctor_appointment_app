package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


/**
 * Repository interface for managing slots.
 * <p>
 * Provides methods to interact with the database for slot-related operations.
 */
public interface SlotRepository extends JpaRepository<Slot, Integer> {

    /**
     * Finds free slots by date and doctor ID.
     *
     * @param date     the date for which to find free slots
     * @param doctorId the ID of the doctor for whom to find free slots
     * @return a list of free slots for the given date and doctor ID
     */
    @Query(value = "SELECT * FROM slot WHERE id NOT IN ( SELECT slot_id FROM appointment WHERE (date = :date) AND (doctor_id = :doctorId));", nativeQuery = true)
    List<Slot> findFreeSlotsByDateAndDoctorId(@Param("date") LocalDate date, @Param("doctorId") int doctorId);

}
