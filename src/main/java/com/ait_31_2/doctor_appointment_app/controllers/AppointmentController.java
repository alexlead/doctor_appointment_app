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

/**
 * Controller class for managing appointments.
 * Handles requests related to appointment management.
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment controller",
        description = "Handles requests related to appointment management.")
public class AppointmentController {

    /**
     * Service layer dependency for handling appointment-related operations.
     * @see AppointmentService
     */
    private final AppointmentService service;

    /**
     * Retrieves all appointments within the specified time period.
     *
     * @param timeStart Start date of the time period
     * @param timeEnd   End date of the time period
     * @return List of AppointmentDto objects representing appointments
     */
    @GetMapping("/{timeStart}/{timeEnd}")
    @Operation(
            summary = "Get all appointments",
            description = "Provides a list of appointments for the authenticated user within a specified time period."
    )
    public List<AppointmentDto> getAllAppointments(@PathVariable @Parameter(description = "2024-02-25") LocalDate timeStart,
                                                   @PathVariable @Parameter(description = "2024-03-25") LocalDate timeEnd) {
        return service.getAllAppointments(timeStart, timeEnd);
    }

    /**
     * Retrieves all future appointments for the authenticated user.
     *
     * @return List of AppointmentDto objects representing future appointments
     */
    @GetMapping("/upcoming/")
    @Operation(
            summary = "Get future appointments",
            description = "Retrieves all upcoming appointments for the authenticated user (doctor or patient)."
    )
    public List<AppointmentDto> getFutureAppointments() {
        return service.getFutureAppointmentsPatient();
    }

    /**
     * Retrieves all past appointments for the authenticated user.
     *
     * @return List of AppointmentDto objects representing past appointments
     */
    @GetMapping("/previous/")
    @Operation(
            summary = "Get past appointments",
            description = "Retrieves all past appointments for the authenticated patient."
    )
    public List<AppointmentDto> getPastAppointments() {
        return service.getPastAppointmentsPatient();
    }

    /**
     * Saves a new/updated appointment.
     *
     * @param request AppointmentRequest object containing appointment details
     * @return ID of the saved appointment
     */
    @PostMapping("/")
    @Operation(
            summary = "Save appointment",
            description = "Saves a new/updated appointment."
    )
    public int save(@RequestBody AppointmentRequest request) {
        return service.saveAppointment(request);
    }

    /**
     * Retrieves the appointment with the specified ID.
     *
     * @param id ID of the appointment to retrieve
     * @return AppointmentDto object representing the appointment
     * @throws AccessDeniedException if access to the appointment is denied
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get appointment by ID",
            description = "Retrieves the appointment with the specified ID."
    )
    public AppointmentDto getById(@PathVariable int id) throws AccessDeniedException {
        return service.getAppointmentById(id);
    }

    /**
     * Deletes the appointment with the specified ID.
     *
     * @param id ID of the appointment to delete
     * @throws AccessDeniedException if access to the appointment is denied
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete appointment",
            description = "Deletes the appointment with the specified ID."
    )
    public void delete(@PathVariable int id) throws AccessDeniedException {
        service.deleteById(id);
    }


}
