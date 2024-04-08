package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.SlotResponse;
import com.ait_31_2.doctor_appointment_app.services.SlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Controller class for managing slots.
 * Handles requests related to slots.
 */
@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
@Tag(name = "Slot controller",
        description = "Handles requests related to slots.")
public class SlotController {
    /**
     * @see SlotService for handling slot operations.
     */
    private final SlotService service;

//    @GetMapping("/")
//    public List<Slot> getAll() {
//        return service.getAllSlots();
//    }
    /**
     * Retrieves slots for the specified date and doctor.
     *
     * @param date The date for which slots are to be retrieved.
     * @param id   The ID of the doctor for whom slots are to be retrieved.
     * @return a SlotResponse containing the slots available for the specified date and doctor.
     */
    @GetMapping("/{date}/{id}")
    @Operation(
            summary = "Slots",
            description = "Displays slots for the current date 'Doctor appointment system'. Available to patients, doctors and administration."
    )
    public SlotResponse getSlots(@PathVariable @Parameter(description = "2024-03-25") LocalDate date,
                                 @PathVariable @Parameter(description = "6") int id) {
        return service.getSlotsList(date, id);
    }


}
