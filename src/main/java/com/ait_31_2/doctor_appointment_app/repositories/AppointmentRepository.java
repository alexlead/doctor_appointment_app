package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {



}
