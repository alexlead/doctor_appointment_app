package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.AppointmentRequest;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.AccessDeniedException;
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

    @GetMapping("/{timeStart}/{timeEnd}")
    @Operation(
            summary = "",
            description = ""
    )
    public List<AppointmentDto> getAllAppointments(@PathVariable @Parameter(description = "2024-02-25") LocalDate timeStart,
                                                   @PathVariable @Parameter(description = "2024-03-25") LocalDate timeEnd) {
        return service.getAllAppointments(timeStart, timeEnd);
    }

    @GetMapping("/patient/future/")
    public List<AppointmentDto> getFutureAppointments() {
        return service.getFutureAppointmentsPatient();
    }

    @GetMapping("patient/past/")
    public List<AppointmentDto> getPastAppointments() {
        return service.getPastAppointmentsPatient();
    }


    @PostMapping("/")
    public int save(@RequestBody AppointmentRequest request) {
        return service.saveAppointment(request);
    }

    @GetMapping("/{id}")
    public AppointmentDto getById(@PathVariable int id)throws AccessDeniedException {
        return service.getAppointmentById(id);
    }


}
