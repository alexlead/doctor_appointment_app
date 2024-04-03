package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.NewAppointmentRequest;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment controller",
        description = "")
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping("/patient/{timeStart}/{timeEnd}")
    @Operation(
            summary = "",
            description = ""
    )
    public List<AppointmentDto> getAllAppointments(@PathVariable @Parameter(description = "2024-02-25") LocalDate timeStart,
                                                   @PathVariable @Parameter(description = "2024-03-25") LocalDate timeEnd) {
        return service.getAllAppointmentsPatient(timeStart, timeEnd);
    }

    @GetMapping("/patient/future/{patientId}")
    public List<AppointmentDto> getFutureAppointments(@PathVariable int patientId) {
        return service.getFutureAppointmentsPatient(patientId);
    }

    @GetMapping("patient/past/{patientId}")
    public List<AppointmentDto> getPastAppointments(@PathVariable int patientId) {
        return service.getPastAppointmentsPatient(patientId);
    }


    @PostMapping("/new")
    public int save(@RequestBody NewAppointmentRequest request) {
        return service.saveNewAppointment(request);
    }


}
