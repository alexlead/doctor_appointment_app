package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.SlotResponse;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
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
import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
@Tag(name = "Slot controller",
        description = "")
public class SlotController {

    private final SlotService service;

//    @GetMapping("/")
//    public List<Slot> getAll() {
//        return service.getAllSlots();
//    }

    @GetMapping("/{date}/{id}")
    @Operation(
            summary = "Slots",
            description = "Displays slots for the current date 'Doctor appointment system'. Available to patient and administration."
    )
    public SlotResponse getSlots(@PathVariable @Parameter(description ="2024-03-25") LocalDate date,
                                 @PathVariable @Parameter(description ="6")int id) {
        return service.getSlotsList(date, id);
    }


}
