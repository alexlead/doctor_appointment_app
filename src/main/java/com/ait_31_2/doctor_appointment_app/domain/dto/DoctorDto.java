package com.ait_31_2.doctor_appointment_app.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * DTO class representing a doctor.
 * Contains basic information about a doctor, including ID, name, surname, and photo.
 */
@Schema(description = "DTO representing a doctor")
public class DoctorDto {
    @Schema(description = "The ID of the doctor",
            example = "2")
    private int id;
    @Schema(description = "The Name of the doctor",
            example = "Michael")
    private String name;
    @Schema(description = "The Surname of the doctor",
            example = "Smith")
    private String surname;
    @Schema(description = "The Photo of the doctor")
    private String photo;

    /**
     * Constructs a new DoctorDto object with the specified parameters.
     *
     * @param id      the ID of the doctor
     * @param name    the name of the doctor
     * @param surname the surname of the doctor
     * @param photo   the photo of the doctor
     */
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
