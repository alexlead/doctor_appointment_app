package com.ait_31_2.doctor_appointment_app.domain.dto;

import jakarta.persistence.*;

import java.util.Objects;


public class DoctorDto {
    private int id;
    private String name;
    private String surname;
    private String photo;

    public DoctorDto(int id, String name, String surname, String photo) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorDto doctorDto = (DoctorDto) o;
        return id == doctorDto.id && Objects.equals(name, doctorDto.name) && Objects.equals(surname, doctorDto.surname) && Objects.equals(photo, doctorDto.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, photo);
    }

    @Override
    public String toString() {
        return "DoctorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
