package com.ait_31_2.doctor_appointment_app.domain.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;

import java.util.List;

public interface RoleInterface {

    int getId();
    public List<User> getUsers();
    void setUsers(List<User> users);

}
