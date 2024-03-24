package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.SlotRequest;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService service;

    @PostMapping("/free_slots")
    public List<Slot> getAllFreeSlot(@RequestBody SlotRequest request) {
        Date date = request.getDate();
        String name = request.getName();
        String surname = request.getSurname();
        return service.getAllFreeSlotByDateAndDoctor(date, name, surname);
    }
}
