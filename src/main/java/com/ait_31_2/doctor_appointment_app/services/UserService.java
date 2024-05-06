package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.RegistrationForm;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.DoctorDto;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.DoctorNotFoundException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserAlreadyExistsException;
import com.ait_31_2.doctor_appointment_app.repositories.UserMetaRepository;
import com.ait_31_2.doctor_appointment_app.repositories.UserRepository;
import com.ait_31_2.doctor_appointment_app.security.security_dto.RefreshToken;
import com.ait_31_2.doctor_appointment_app.security.security_dto.TokenResponseDto;
import com.ait_31_2.doctor_appointment_app.security.security_service.TokenService;
import com.ait_31_2.doctor_appointment_app.services.mapping.UserMappingService;
import jakarta.persistence.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for managing users.
 *
 * @author Tetiana
 * @version 1.1.0
 */
@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private UserMetaRepository doctorDtoRepository;
    private UserMappingService mapping;
    private BCryptPasswordEncoder encoder;
    private TokenService tokenService;
    private Map<String, String> refreshStorage;

    /**
     * Constructs a new UserService with the specified parameters.
     *
     * @param repository          the {@link UserRepository} to interact with the database
     * @param doctorDtoRepository the {@link UserMetaRepository} to fetch doctor data
     * @param mapping             the {@link UserMappingService} for mapping user data
     * @param encoder             the {@link BCryptPasswordEncoder} for password encoding
     * @param tokenService        the {@link TokenService} for generating authentication tokens
     */
    public UserService(UserRepository repository, UserMetaRepository doctorDtoRepository,
                       UserMappingService mapping, BCryptPasswordEncoder encoder, TokenService tokenService) {
        this.repository = repository;
        this.doctorDtoRepository = doctorDtoRepository;
        this.mapping = mapping;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.refreshStorage = new HashMap<>();
    }

    /**
     * Registers a new user.
     *
     * @param form the {@link RegistrationForm} containing user details
     * @return a {@link TokenResponseDto} containing access and refresh tokens
     * @throws UserAlreadyExistsException if a user with the same username already exists
     */
    @Transactional
    public TokenResponseDto registerUser(RegistrationForm form, HttpServletRequest request) {
        User foundUser = repository.findByUsername(form.getUsername());
        if (foundUser != null) {
            throw new UserAlreadyExistsException("User with this name already exists!");
        }
        User user = mapping.mapRegistrationFormToUser(form);
        User newUser = repository.save(user);

        String accessToken = tokenService.generateAccessToken(newUser, request);
        RefreshToken refreshToken = tokenService.generateRefreshToken(newUser, request);

        return new TokenResponseDto(accessToken,refreshToken.getToken() ,
                "User " + newUser.getName() + " " + newUser.getSurname() + " successfully registered!");


    }

    /**
     * Retrieves list of all users.
     *
     * @return a list of {@link UserDto}
     */
    public List<UserDto> getAllUser() {
        return repository.findAll()
                .stream()
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }

    /**
     * Retrieves list of all doctors.
     *
     * @return a list of {@link UserDto}
     */
    public List<UserDto> getAllDoctors() {
        return getUserByRole("ROLE_DOCTOR");

    }

    /**
     * Retrieves list of all doctors with photo.
     *
     * @return a list of {@link DoctorDto}
     */
    public List<DoctorDto> getAllDoctorsWithPhoto() {

        Collection<Tuple> doctors = doctorDtoRepository.getAllDoctorsWithPhoto();

        List<DoctorDto> doctorDtos = doctors.stream()
                .map(t -> new DoctorDto(
                        t.get(0, Integer.class),
                        t.get(1, String.class),
                        t.get(2, String.class),
                        t.get(3, String.class)

                ))
                .toList();

        return doctorDtos;
    }

    /**
     * Retrieves the {@link UserDto} representation of a doctor by their ID.
     *
     * @param doctorId the ID of the doctor to retrieve
     * @return the UserDto representing the doctor
     * @throws DoctorNotFoundException if the doctor with the specified ID is not found or does not have the 'ROLE_DOCTOR' role
     */
    public UserDto getDoctorById(int doctorId) {
        User doctor = repository.findById(doctorId).orElse(null);

        if (doctor == null || !hasRole(doctor, "ROLE_DOCTOR")) {
            throw new DoctorNotFoundException("Doctor was not found!");
        }
        return mapping.mapUserToDtoName(doctor);
    }

    /**
     * Checks if the specified user has the given role.
     *
     * @param user     the user to check for the role
     * @param roleName the name of the role to check
     * @return true if the user has the specified role, otherwise false
     */
    private boolean hasRole(User user, String roleName) {
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a list of {@link UserDto} representations of patients by their partial name.
     *
     * @param partName the partial name of the patients to search for
     * @return the list of UserDto representing the patients
     */
    public List<UserDto> getPatientByName(String partName) {
        return repository.findUserByPartName(partName)
                .stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getAuthority().equals("ROLE_PATIENT")))
                .map(user -> mapping.mapUserToDto(user))
                .toList();
    }

    /**
     * Retrieves a list of {@link UserDto} with the specified role.
     *
     * @param role the role to filter users by
     * @return a list of user DTOs with the specified role
     */
    public List<UserDto> getUserByRole(String role) {
        return repository.findAllByRole(role)
                .stream()
                .map(user -> mapping.mapUserToDtoName(user))
                .toList();
    }

    /**
     * Retrieves the {@link UserDto} representation of a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the UserDto representing the user
     * @throws UsernameNotFoundException if the user with the specified ID is not found
     */
    public UserDto getUserById(int id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return mapping.mapUserToDto(user);
    }


    /**
     * Loads the UserDetails for the specified username. Overridden method of UserDetailsService, is using Spring security
     *
     * @param username the username of the user to load
     * @return the UserDetails representing the user
     * @throws UsernameNotFoundException if the user with the specified username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;

    }


}
