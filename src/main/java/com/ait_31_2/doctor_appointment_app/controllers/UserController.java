package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.responses.UserSuccessRegistration;
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
    //TODO endpoints???

    @PostMapping("/registration")
    public void register(@RequestBody User user) throws UserSuccessRegistration {
         service.registerUser(user);
    }


    @GetMapping("/all")
    public List<UserDto> getAll() {
        return service.getAllUser();
    }

    @GetMapping("/by_id/{userid}")
    public UserDto getById(@PathVariable int userid) {
        return service.getUserById(userid);
    }


}
