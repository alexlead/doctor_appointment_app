package com.ait_31_2.doctor_appointment_app.domain;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import lombok.Data;

import java.util.List;
@Data
public class SlotResponse {
    private List<Slot> all;
    private List<Slot> free;
}
