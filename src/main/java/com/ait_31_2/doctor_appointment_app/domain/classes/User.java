package com.ait_31_2.doctor_appointment_app.domain.classes;

import com.ait_31_2.doctor_appointment_app.domain.interfaces.UserInterface;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "user")
public class User implements UserInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    @NotEmpty
    private String name;

    @Column(name = "surname")
    @NotNull
    @NotEmpty
    private String surname;

    @Column(name = "email")
    @Email(message = "Incorrect email values are not allowed")
    private String email;

    @Column(name = "password")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "The password must contain at least one digit, one lowercase and one uppercase letter, " +
                    "one special character, and be at least 8 characters long."
    )
    private String password;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User() {
    }

    public User(int id, String name, String surname, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;

        this.password = password;
        this.role = role;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
