package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.RegistrationForm;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.Response;
import com.ait_31_2.doctor_appointment_app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User controller",
        description = "")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping("/registration")
    @Operation(
            summary = "Registration",
            description = "Registration in app 'Doctor appointment system'. Available to all users."
    )
    public Response register(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User`s object")
            RegistrationForm form
            ) {
        Response response = service.registerUser(form);
        return response;
    }

    @GetMapping("/")
    @Operation(
            summary = "List of users",
            description = "View list of all users. Available to administration."
    )
    public List<UserDto> getAll() {
        return service.getAllUser();
    }

    @GetMapping("/doctors")
    @Operation(
            summary = "Doctors",
            description = "View list of all doctors. Available to all users."
    )
    public List<UserDto> getAllDoctors() {
        return service.getAllDoctors();
    }

    @GetMapping("/doctor/{id}")
    @Operation(
            summary = "Find doctor",
            description = "Search for a doctor by id. Available to registered patients and administration."
    )
    public UserDto getDoctor(
            @PathVariable
            @Parameter(description ="User`s id")int id) {
        return service.getDoctorById(id);
    }

    @GetMapping("/patient/{partName}")
    @Operation(
            summary = "Find patient",
            description = "Search for a patient by first letter of name. Available to doctors and administration."
    )
    public List<UserDto> getPatient(@PathVariable String partName) {
        return service.getPatientByName(partName);
    }

    @GetMapping("/{userid}")
    @Operation(
            summary = "Find user",
            description = "Search for a user by id. Available to administration."
    )
    public UserDto getById(@PathVariable int userid) {
        return service.getUserById(userid);
    }


}
