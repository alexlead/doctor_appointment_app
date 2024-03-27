package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService service;
    @GetMapping("patient/appointments/{patientId}/{timeStart}/{timeEnd}")
    public List<AppointmentDto> getAllAppointments(@PathVariable int patientId, @PathVariable Date timeStart, @PathVariable Date timeEnd){
        return service.getAllAppointmentsPatient(patientId,timeStart,timeEnd);
    }


}
