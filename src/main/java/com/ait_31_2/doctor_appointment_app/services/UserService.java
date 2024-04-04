package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.RegistrationForm;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.DoctorNotFoundException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserAlreadyExistsException;
import com.ait_31_2.doctor_appointment_app.repositories.UserRepository;
import com.ait_31_2.doctor_appointment_app.security.security_dto.TokenResponseDto;
import com.ait_31_2.doctor_appointment_app.security.security_service.TokenService;
import com.ait_31_2.doctor_appointment_app.services.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private UserMappingService mapping;
    private BCryptPasswordEncoder encoder;
    @Autowired
    private TokenService tokenService;
    private Map<String, String> refreshStorage;


    public UserService(UserRepository repository, UserMappingService mapping, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.mapping = mapping;
        this.encoder = encoder;
        this.refreshStorage = new HashMap<>();
    }

    @Transactional
    public TokenResponseDto registerUser(RegistrationForm form) {
        User foundUser = repository.findByUsername(form.getUsername());
        if (foundUser != null) {
            throw new UserAlreadyExistsException("User with this name already exists!");
        }
        User user = mapping.mapRegistrationFormToUser(form);
        User newUser = repository.save(user);
        String username = newUser.getUsername();
        String accessToken = tokenService.generateAccessToken(newUser);
        String refreshToken = tokenService.generateRefreshToken(newUser);
        refreshStorage.put(username, refreshToken);
        return new TokenResponseDto(accessToken, refreshToken,
                "User " + newUser.getName() + " " + newUser.getSurname() + " successfully registered!");


    }


    public List<UserDto> getAllUser() {
        return repository.findAll()
                .stream()
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }


    public List<UserDto> getAllDoctors() {
        return getUserByRole("ROLE_DOCTOR");

    }


    public UserDto getDoctorById(int doctorId) {
        User doctor = repository.findById(doctorId).orElse(null);

        if (doctor == null || !hasRole(doctor, "ROLE_DOCTOR")) {
            throw new DoctorNotFoundException("Doctor was not found!");
        }
        return mapping.mapUserToDtoName(doctor);
    }

    private boolean hasRole(User user, String roleName) {
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }


    public List<UserDto> getPatientByName(String partName) {
        return repository.findUserByPartName(partName)
                .stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getAuthority().equals("ROLE_PATIENT")))
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }


    public List<UserDto> getUserByRole(String role) {
        return repository.findAllByRole(role)
                .stream()
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }


    public UserDto getUserById(int id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return mapping.mapUserToDto(user);
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
