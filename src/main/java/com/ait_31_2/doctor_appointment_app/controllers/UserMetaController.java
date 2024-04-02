package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.dto.UserMetaDto;
import com.ait_31_2.doctor_appointment_app.services.UserMetaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class UserMetaController {

    private UserMetaService service;

    public UserMetaController(UserMetaService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserMetaDto> getUserProfileById() {
        return service.getUserProfileById();
    }

    @PutMapping
    public void updateUserProfileById(@RequestBody List<UserMetaDto> profile) {
        service.updateUserProfileById(profile);
    }
    @DeleteMapping
    public void deleteUserProfileFields( @RequestBody List<UserMetaDto> profile) {
        service.deleteByUserIdAndMetaKey( profile);
    }

}