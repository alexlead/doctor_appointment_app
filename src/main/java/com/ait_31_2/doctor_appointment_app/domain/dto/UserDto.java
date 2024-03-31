package com.ait_31_2.doctor_appointment_app.domain.dto;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
import java.util.Set;

public class UserDto {

    private int id;
    private String name;
    private String surname;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Role> roles;

    public UserDto(int id, String name, String surname, String username, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.roles = roles;
    }

    public UserDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto userDto)) return false;
        return getId() == userDto.getId() && Objects.equals(getName(), userDto.getName()) && Objects.equals(getSurname(), userDto.getSurname()) && Objects.equals(getUsername(), userDto.getUsername()) && Objects.equals(getRoles(), userDto.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getUsername(), getRoles());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", role=" + roles +
                '}';
    }
}
