package com.ait_31_2.doctor_appointment_app.domain.dto;

import java.util.Objects;

public class DoctorDto {
    private int id;
    private String name;
    private String surname;
    private String photoBase64;

    public DoctorDto(int id, String name, String surname, String photoBase64) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.photoBase64 = photoBase64;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorDto doctorDto)) return false;
        return id == doctorDto.id && Objects.equals(name, doctorDto.name) && Objects.equals(surname, doctorDto.surname) && Objects.equals(photoBase64, doctorDto.photoBase64);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, photoBase64);
    }

    @Override
    public String toString() {
        return "DoctorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", photoBase64='" + photoBase64 + '\'' +
                '}';
    }
}
