package com.ait_31_2.doctor_appointment_app.domain;


import java.sql.Date;
import java.time.LocalDate;

@lombok.Data
public class SlotRequest {

    private LocalDate date;
    private String name;
    private String surname;


}