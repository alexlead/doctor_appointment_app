package com.ait_31_2.doctor_appointment_app.domain.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.repositories.SlotRepository;

import java.sql.Time;



public interface SlotInterface {

    int getId();

    Time getStartTime();

    void setStartTime(Time startTime);

    Time getEndTime();

    void setEndTime(Time endTime);

}

