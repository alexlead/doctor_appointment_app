package com.ait_31_2.doctor_appointment_app.services;


import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import com.ait_31_2.doctor_appointment_app.services.interfaces.SlotServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SlotService implements SlotServiceInterface {


    private SlotRepository repository;

    public SlotService(SlotRepository repository) {
        this.repository = repository;
    }




    @Override
    public List<Slot> getAllSlots() {
        return repository.findAll();
    }

    @Override
    public Slot getSlotById(int id) {
        return repository.findById(id).orElse(null);
    }




}
