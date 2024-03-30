package com.ait_31_2.doctor_appointment_app.services.mapping;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserMappingService {


    public UserDto mapUserToDto(User user) {
        int id = user.getId();
        String name = user.getName();
        String surname = user.getSurname();
        String username = user.getUsername();
        Role role = user.getRole();
        return new UserDto(id, name, surname, username, role);

    }

    public UserDto mapUserToDtoName(User user) {
        int id = user.getId();
        String name = user.getName();
        String surname = user.getSurname();

        return new UserDto(id, name, surname,null,null);

    }




    public User mapUserDtoToEntityUser(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());

        return user;
    }


}