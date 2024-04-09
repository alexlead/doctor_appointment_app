package com.ait_31_2.doctor_appointment_app.services.mapping;

import com.ait_31_2.doctor_appointment_app.domain.RegistrationForm;
import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * Service class for mapping user entities to DTOs and vice versa.
 */
@Service
@RequiredArgsConstructor
public class UserMappingService {
    /**
     * The BCrypt password encoder used for encoding passwords.
     */
    private final BCryptPasswordEncoder encoder;

    /**
     * Maps a user entity to a DTO for user view.
     *
     * @param user The {@link User} entity to map.
     * @return A  {@link UserDto} for user view.
     */
    public UserDto mapUserToDto(User user) {
        int id = user.getId();
        String name = user.getName();
        String surname = user.getSurname();
        String username = user.getUsername();
        Set<Role> roles = user.getRoles();
        return new UserDto(id, name, surname, username, roles);

    }
    /**
     * Maps a user entity to a DTO for user view, including user ID,  Name and  Surname.
     *
     * @param user The {@link User} entity to map.
     * @return A  {@link UserDto} for user view.
     */
    public UserDto mapUserToDtoName(User user) {
        int id = user.getId();
        String name = user.getName();
        String surname = user.getSurname();

        return new UserDto(id, name, surname,null,null);

    }

    /**
     * Maps a {@link RegistrationForm} object to a User object.
     *
     * @param form The RegistrationForm object containing user registration data.
     * @return The User object created from the registration form data.
     */
    public User  mapRegistrationFormToUser(@Nonnull RegistrationForm form){
        User user = new User();
        user.setId(0);
        user.setName(form.getName());
        user.setSurname(form.getSurname());
        user.setUsername(form.getUsername());
        String encodedPassword = encoder.encode(form.getPassword());
        user.setPassword(encodedPassword);
        Set<Role> roles = new HashSet<>();
        Role role = new Role(1, "ROLE_PATIENT");
        roles.add(role);
        user.setRoles(roles);
        return user;


    }
    /**
     * Maps a {@link UserDto} entity to a {@link User} for user view.
     *
     * @param dto The userDto to map.
     * @return A  {@link User} for user view.
     */
    public User mapUserDtoToEntityUser(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setUsername(dto.getUsername());
        user.setRoles(dto.getRoles());

        return user;
    }


}