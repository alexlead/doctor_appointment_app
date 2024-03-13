package com.ait_31_2.doctor_appointment_app.domain.interfaces;


import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;

import java.util.List;

public interface SlotInterface {

    List<Slot> getAllSlots();
    Slot getSlotById(int id);
    Slot createSlot(Slot slot);

}

