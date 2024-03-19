package com.ait_31_2.doctor_appointment_app.services.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;

import java.util.List;

public interface SlotServiceInterface {
    List<Slot> getAllSlots();
    Slot getSlotById(int id);
    Slot createSlot(Slot slot);
    Slot updateSlot(int id, Slot slot);
    void deleteSlot(int id);
}
