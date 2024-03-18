package com.ait_31_2.doctor_appointment_app.services.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.Response;
import com.ait_31_2.doctor_appointment_app.exception_handling.responses.UserSuccessRegistration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
   void registerUser(User user) throws UserSuccessRegistration;
    UserDto updateUser(UserDto userDto);
    List<UserDto> getAllUser();
    List<UserDto> getUserByRole(Role role);
    UserDto getUserById(int id);
}
