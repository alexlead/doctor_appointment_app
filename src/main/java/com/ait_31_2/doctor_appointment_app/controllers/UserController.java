package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.LoginForm;
import com.ait_31_2.doctor_appointment_app.exception_handling.Response;
import com.ait_31_2.doctor_appointment_app.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping("/registration")
    public Response register(@RequestBody User user) {
        Response response = service.registerUser(user);
        return response;
    }

    @PostMapping("/authorisation")
    public Response authorisation(@RequestBody LoginForm loginForm) {
        String username = loginForm.getUsername();

        Response response = service.authorization(username, loginForm.getPassword());
        return response;
    }


    @GetMapping("/all")
    public List<UserDto> getAll() {
        return service.getAllUser();
    }

    @GetMapping("/doctors")
    public List<UserDto> getAllDoctors() {
        return service.getAllDoctors();
    }

    @GetMapping("/doctor_name/{surname}")
    public UserDto getDoctor(@PathVariable String surname) {
        return service.getDoctorByName(surname);
    }

    @GetMapping("/patient_name/{partName}")
    public List<UserDto> getPatient(@PathVariable String partName){
        return  service.getPatientByName(partName);
    }

    @GetMapping("/by_id/{userid}")
    public UserDto getById(@PathVariable int userid) {
        return service.getUserById(userid);
    }


}
