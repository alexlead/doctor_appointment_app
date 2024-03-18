package com.ait_31_2.doctor_appointment_app.domain.services;


import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.repositories.SlotRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SlotService {

    private SlotRepository repository;

    public SlotService(SlotRepository repository) {
        this.repository = repository;
    }

    public List<Slot> getAll() {
        return repository.findAll();
    }

    public Slot getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Slot save(Slot slot) {
        return repository.save(slot);
    }



}
