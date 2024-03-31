package com.ait_31_2.doctor_appointment_app.services;


import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.NotFreeSlotsException;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class SlotService  {


    private SlotRepository repository;
    private UserService userService;

    public SlotService(SlotRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }



    public List<Slot> getAllSlots() {
        return repository.findAll();
    }


    public Slot getSlotById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Slot> getAllFreeSlotByDateAndId(LocalDate date, int doctorId) {
      //TODO
        List<Slot> slots =  repository.findFreeSlotsByDateAndDoctor(date, doctorId);
        if (slots==null){
            throw new NotFreeSlotsException("There are no slots on this date!");
        }

        return slots;
    }




}
