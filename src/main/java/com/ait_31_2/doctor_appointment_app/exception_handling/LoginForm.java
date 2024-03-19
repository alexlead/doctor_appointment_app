package com.ait_31_2.doctor_appointment_app.exception_handling;

public class LoginForm {

    private String username;
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
}
