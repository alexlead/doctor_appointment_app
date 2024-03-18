package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.Response;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserAlreadyExistsException;
import com.ait_31_2.doctor_appointment_app.exception_handling.responses.UserSuccessRegistration;
import com.ait_31_2.doctor_appointment_app.repositories.UserRepository;
import com.ait_31_2.doctor_appointment_app.services.interfaces.UserServiceInterface;
import com.ait_31_2.doctor_appointment_app.services.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    private UserRepository repository;
    private UserMappingService mapping;
    private BCryptPasswordEncoder encoder;

    public UserService(UserRepository repository, UserMappingService mapping, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.mapping = mapping;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public void registerUser(User user) throws UserSuccessRegistration  {
        User foundUser = repository.findByUsername(user.getUsername());
        if (foundUser != null) {
            throw new UserAlreadyExistsException("User with this name already exists!");
        }
        user.setId(0);
        user.clearRole();
        Role role = new Role(1, "ROLE_PATIENT");
        user.setRole(role);

        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repository.save(user);

        throw new UserSuccessRegistration("User " + user.getName() + " " + user.getSurname() + " successfully registered!");
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public List<UserDto> getAllUser() {
        return repository.findAll()
                .stream()
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }

    @Override
    public List<UserDto> getUserByRole(Role role) {
        return null;
    }

    @Override
    public UserDto getUserById(int id) {
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            return mapping.mapUserToDto(user);
        }

        return null;
    }

    //Spring security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;

    }
}
