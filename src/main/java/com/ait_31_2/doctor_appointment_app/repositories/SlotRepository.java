package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SlotRepository extends JpaRepository<Slot, Integer> {

}
