package com.ait_31_2.doctor_appointment_app.services.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.Response;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
    Response registerUser(User user);

    Response authorization(String username, String password);


    List<UserDto> getAllUser();

    List<UserDto> getAllDoctors();

    UserDto getDoctorByName(String surname);

    List<UserDto> getPatientByName(String partName);

    List<UserDto> getUserByRole(String role);

    UserDto getUserById(int id);

    /**
     * Registration (Sign Up) (Access: Unregistered)
     *
     * Authorization (Login / Sign in) (Access: Unregistered)
     *
     * Get All Doctors (Access: Pacient / Unregistered)
     *
     * Get Doctor By Name (Access: Pacient)
     *
     * Get Patient By Name ( Access: Doctor / Admin ) - list of Pacient by part of name
     *
     * Logout (Access: all logged in)
     *
     * Get All By Role (Access: Admin)
     *
     * Get User By Id (Access: Admin)
     */
}
