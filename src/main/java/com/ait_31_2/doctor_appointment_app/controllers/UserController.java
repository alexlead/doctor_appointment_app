package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.RegistrationForm;
import com.ait_31_2.doctor_appointment_app.domain.dto.DoctorDto;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.security.security_dto.TokenResponseDto;
import com.ait_31_2.doctor_appointment_app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller class for managing users.
 * Handles requests related to user registration, retrieval, and search.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User controller",
        description = "Handles requests related to user registration, retrieval, and search.")
public class UserController {
    /**
     * Service layer dependency for handling user-related operations.
     * @see UserService
     */
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Registers a new user in the application.
     *
     * @param form     RegistrationForm object containing user registration data.
     * @param response HttpServletResponse object used to set authentication cookie.
     * @return ResponseEntity<TokenResponseDto> containing access token for the newly registered user.
     */
    @PostMapping("/registration")
    @Operation(
            summary = "Registration",
            description = "Registration in app 'Doctor appointment system'. Available to all users."
    )
    public ResponseEntity<TokenResponseDto> register(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User`s object")
            RegistrationForm form, HttpServletResponse response
            ){
        TokenResponseDto tokenDto = service.registerUser(form);

        Cookie cookie = new Cookie("Access-Token", tokenDto.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(tokenDto);
    }

    /**
     * Retrieves a list of all users.
     * Available to administration.
     * @return a list of {@link UserDto} objects representing users.
     */
    @GetMapping("/")
    @Operation(
            summary = "Get list of all users",
            description = "View list of all users. Available to administration."
    )
    public List<UserDto> getAll() {
        return service.getAllUser();
    }

    /**
     * Retrieves a list of all doctors.
     * Available to all users.
     * @return a list of {@link UserDto} objects representing doctors.
     */
    @GetMapping("/doctors/")
    @Operation(
            summary = "Get list of all doctors",
            description = "View list of all doctors. Available to all users."
    )
    public List<UserDto> getAllDoctors() {
        return service.getAllDoctors();
    }

    /**
     * Retrieves a list of all doctors with their photos.
     * Available to all users.
     * @return a list of {@link DoctorDto} objects representing doctors with photos.
     */
    @GetMapping("/doctorslist/")
    @Operation(
            summary = "Get list of all doctors with photo",
            description = "View list of all doctors. Available to all users."
    )
    public List<DoctorDto> getAllDoctorsWithPhoto() {
        return service.getAllDoctorsWithPhoto();
    }

    /**
     * Retrieves a doctor by ID.
     * Available to registered patients and administration.
     * @param id the ID of the doctor to retrieve
     * @return a {@link UserDto} object representing the doctor
     */
    @GetMapping("/doctor/{id}")
    @Operation(
            summary = "Get doctor by ID",
            description = "Search for a doctor by id. Available to registered patients and administration."
    )
    public UserDto getDoctor(
            @PathVariable
            @Parameter(description ="User`s id")int id) {
        return service.getDoctorById(id);
    }

    /**
     * Retrieves patients by the first letter of their name.
     * Available to doctors.
     * @param partName the first letter of the patient's name
     * @return a list of {@link UserDto} objects representing patients
     */
    @GetMapping("/patient/{partName}")
    @Operation(
            summary = "Get patients by the first letters of their name ",
            description = "Search for a patient by first letter of name. Available to doctors."
    )
    public List<UserDto> getPatient(@PathVariable String partName) {
        return service.getPatientByName(partName);
    }

    /**
     * Retrieves a user by ID.
     * Available to administration.
     * @param userid the ID of the user to retrieve
     * @return a {@link UserDto} object representing the user
     */
    @GetMapping("/{userid}")
    @Operation(
            summary = "Get user by ID",
            description = "Search for a user by id. Available to administration."
    )
    public UserDto getById(@PathVariable int userid) {
        return service.getUserById(userid);
    }


}
