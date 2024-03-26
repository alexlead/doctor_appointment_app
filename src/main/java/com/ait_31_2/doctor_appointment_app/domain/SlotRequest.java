package com.ait_31_2.doctor_appointment_app.domain;


import java.sql.Date;

@lombok.Data
public class SlotRequest {

    private Date date;
    private String name;
    private String surname;


}
