package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.Response;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.DoctorNotFoundException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UnauthorizedException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserAlreadyExistsException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserNotFoundException;
import com.ait_31_2.doctor_appointment_app.repositories.UserRepository;
import com.ait_31_2.doctor_appointment_app.services.interfaces.UserServiceInterface;
import com.ait_31_2.doctor_appointment_app.services.mapping.UserMappingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

    private UserRepository repository;
    private UserMappingService mapping;
    private BCryptPasswordEncoder encoder;
    @Autowired
    private HttpServletRequest request;

    public UserService(UserRepository repository, UserMappingService mapping, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.mapping = mapping;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public Response registerUser(User user) {
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

        return new Response("OK", "User " + user.getName() + " " + user.getSurname() + " successfully registered!");
    }

    @Transactional
    @Override
    public Response authorization(String username, String password) {

        User foundUser = repository.findByUsername(username);

        if (foundUser == null || !encoder.matches(password, foundUser.getPassword())) {
            throw new UnauthorizedException("Invalid username or password!");
        }

        return new Response("OK", "User " + foundUser.getName() + " " + foundUser.getSurname() + " successfully authorized!");
    }


    @Override
    public List<UserDto> getAllUser() {
        return repository.findAll()
                .stream()
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }

    @Override
    public List<UserDto> getAllDoctors() {
        return repository.findAllByRole("ROLE_DOCTOR")
                .stream()
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }

    @Override
    public UserDto getDoctorByName(String name, String surname) {
        User doctor = repository.findUserByNameAndRole(name, surname, "ROLE_DOCTOR");
        if (doctor == null) {
            throw new DoctorNotFoundException("No doctor with that name was found!");
        }
        return mapping.mapUserToDto(doctor);
    }

    @Override
    public List<UserDto> getPatientByName(String partName) {
        return repository.findUserByPartName(partName)
                .stream()
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }

    @Override
    public List<UserDto> getUserByRole(String role) {
        return repository.findAllByRole(role)
                .stream()
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }


    @Override
    public UserDto getUserById(int id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }

        return mapping.mapUserToDto(user);
    }

    @Override
    public Response logout() {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, null, auth);
            }
            return new Response("OK", "Logout successful!");
        } catch (Exception e) {

            return new Response("ERROR", "Logout failed! ");
        }
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
