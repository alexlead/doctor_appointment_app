package com.ait_31_2.doctor_appointment_app.domain;

import javax.xml.crypto.Data;
import java.util.Date;

@lombok.Data
public class SlotRequest {

    private Date date;
    private String name;
    private String surname;


}
