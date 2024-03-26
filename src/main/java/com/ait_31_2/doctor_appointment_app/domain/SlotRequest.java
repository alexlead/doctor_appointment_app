package com.ait_31_2.doctor_appointment_app.domain;


import lombok.Data;

import java.sql.Date;
import java.util.Objects;


public class SlotRequest {

    private Date date;
    private String name;
    private String surname;

    public SlotRequest() {
    }

    public SlotRequest(Date date, String name, String surname) {
        this.date = date;
        this.name = name;
        this.surname = surname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SlotRequest that)) return false;
        return Objects.equals(getDate(), that.getDate()) && Objects.equals(getName(), that.getName()) && Objects.equals(getSurname(), that.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getName(), getSurname());
    }

    @Override
    public String toString() {
        return "SlotRequest{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
