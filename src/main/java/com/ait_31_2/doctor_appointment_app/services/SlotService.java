package com.ait_31_2.doctor_appointment_app.services;


import com.ait_31_2.doctor_appointment_app.domain.SlotResponse;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.NotFreeSlotsException;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing slots.
 */
@Service
public class SlotService {

    private SlotRepository repository;
    private UserService userService;

    /**
     * Constructs a new SlotService with the specified repository and user service.
     *
     * @param repository  the {@link SlotRepository} for slots
     * @param userService the {@link UserService}
     */
    public SlotService(SlotRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    /**
     * Retrieves all slots.
     *
     * @return a list of all {@link Slot}
     */
    private List<Slot> getAllSlots() {
        return repository.findAll();
    }

    /**
     * Retrieves all free slots for a specific date and doctor.
     *
     * @param date     the date for which to retrieve free slots
     * @param doctorId the ID of the doctor
     * @return a list of free slots {@link Slot} for the specified date and doctor
     * @throws NotFreeSlotsException if there are no free slots on the specified date
     */
    private List<Slot> getAllFreeSlotByDateAndDoctorId(LocalDate date, int doctorId) {
        List<Slot> slots = repository.findFreeSlotsByDateAndDoctorId(date, doctorId);
        if (slots == null) {
            throw new NotFreeSlotsException("There are not free slots on this date!");
        }

        return slots;
    }

    /**
     * Retrieves a response containing all slots and free slots for a specific date and doctor.
     *
     * @param date     the date for which to retrieve slots
     * @param doctorId the ID of the doctor
     * @return a {@link SlotResponse} object containing all slots and free slots
     */
    public SlotResponse getSlotsList(LocalDate date, int doctorId) {
        List<Slot> allSlots = getAllSlots();
        List<Slot> freeSlots = getAllFreeSlotByDateAndDoctorId(date, doctorId);
        SlotResponse response = new SlotResponse();
        response.setAll(allSlots);
        response.setFree(freeSlots);
        return response;
    }


}
