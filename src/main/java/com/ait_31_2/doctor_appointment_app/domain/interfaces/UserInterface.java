package com.ait_31_2.doctor_appointment_app.domain.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface UserInterface extends UserDetails {

    int getId();
    void setId(int id);
    String getName();
    void setName(String name);
    String getSurname();
    void setSurname(String surname);
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    Role getRole();
    void setRole(Role roles);
}
