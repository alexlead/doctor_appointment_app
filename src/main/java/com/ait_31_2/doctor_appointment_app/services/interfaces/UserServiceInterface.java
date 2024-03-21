package com.ait_31_2.doctor_appointment_app.services.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
    Response registerUser(User user); // Registration (Sign Up) (Access: Unregistered)

    Response authorization(String username, String password); // Authorization (Login / Sign in) (Access: Unregistered)


    List<UserDto> getAllUser();

    List<UserDto> getAllDoctors(); // Get All Doctors (Access: Pacient / Unregistered)

    UserDto getDoctorByName(String surname); // Get Doctor By Name (Access: Pacient)

    List<UserDto> getPatientByName(String partName); // Get Patient By Name ( Access: Doctor / Admin ) - list of Pacient by part of name

    List<UserDto> getUserByRole(String role); // Get All By Role (Access: Admin)

    UserDto getUserById(int id);// Get User By Id (Access: Admin)

    Response logout(); // Logout (Access: all logged in)


}
