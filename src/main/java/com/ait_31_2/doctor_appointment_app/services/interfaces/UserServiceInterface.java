package com.ait_31_2.doctor_appointment_app.services.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.responses.UserSuccessRegistration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
    void registerUser(User user) throws UserSuccessRegistration;

    UserDto authorization(String username, String password);

    UserDto updateUser(UserDto userDto);

    List<UserDto> getAllUser();

    List<UserDto> getUserByRole(Role role);

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
