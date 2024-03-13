package com.ait_31_2.doctor_appointment_app.domain.interfaces;

import javax.xml.crypto.Data;


public interface SlotInterface {

    int getId();

    Data getStartTime();

    void setStartTime(Data startTime);

    Data getEndTime();

    void setEndTime(Data endTime);

}

