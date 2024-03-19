package com.ait_31_2.doctor_appointment_app.services;


import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import com.ait_31_2.doctor_appointment_app.services.interfaces.SlotServiceInterface;

import java.util.List;

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

    @Override
    public Slot createSlot(Slot slot) {
        return repository.save(slot);
    }

    @Override
    public Slot updateSlot(int id, Slot slot) {
        return null;
    }

    @Override
    public void deleteSlot(int id) {

    } 
}
