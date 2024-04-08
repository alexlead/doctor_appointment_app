package com.ait_31_2.doctor_appointment_app.services;


import com.ait_31_2.doctor_appointment_app.domain.SlotResponse;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.NotFreeSlotsException;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SlotService {


    private SlotRepository repository;
    private UserService userService;

    public SlotService(SlotRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }


    private List<Slot> getAllSlots() {
        return repository.findAll();
    }




    private List<Slot> getAllFreeSlotByDateAndDoctorId(LocalDate date, int doctorId) {
        List<Slot> slots = repository.findFreeSlotsByDateAndDoctorId(date, doctorId);
        if (slots == null) {
            throw new NotFreeSlotsException("There are not free slots on this date!");
        }

        return slots;
    }

    public SlotResponse getSlotsList(LocalDate date, int doctorId) {
        List<Slot> allSlots = getAllSlots();
        List<Slot> freeSlots = getAllFreeSlotByDateAndDoctorId(date, doctorId);
        SlotResponse response = new SlotResponse();
        response.setAll(allSlots);
        response.setFree(freeSlots);
        return response;
    }


}
