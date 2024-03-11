package com.ait_31_2.doctor_appointment_app.domain.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;

public interface UserInterface {

    int getId();
    String getName();
    void setName(String name);
    String getSurname();
    void setSurname(String surname);
    String getEmail();
    void setEmail(String email);
    String getPassword();
    void setPassword(String password);
    Role getRole();
    void setRole(Role role); //TODO как назначется роль?
}
