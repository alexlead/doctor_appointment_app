package com.ait_31_2.doctor_appointment_app.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;
/**
        * Form fot authentication.
        * Contains basic information for authentication, including username and password.
        */
@Schema(
        description = "Login form"
)
public class LoginForm {
    @Schema(
            description = "email, which is a username",
            example = "iv_ivanov@gm.com")
    private String username;
    @Schema(
            description = "Password",
            example = "123S!fghjk")
    private String password;


    public LoginForm() {
    }

    public LoginForm(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginForm loginForm)) return false;
        return Objects.equals(getUsername(), loginForm.getUsername()) && Objects.equals(getPassword(), loginForm.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
