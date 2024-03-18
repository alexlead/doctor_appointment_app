package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.dto.UserMetaDto;
import com.ait_31_2.doctor_appointment_app.services.UserMetaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class UserMetaController {

    private UserMetaService service;

    public UserMetaController(UserMetaService service) {
        this.service = service;
    }

    @GetMapping("/{userid}")
    public List<UserMetaDto> getUserProfileById(@PathVariable int userid) {
        return service.getUserProfileById(userid);
    }

    @PutMapping("/{userid}")
    public void updateUserProfileById(@PathVariable int userid, @RequestBody List<UserMetaDto> profile) {
        service.updateUserProfileById(userid, profile);
    }
    @DeleteMapping("/{userid}")
    public void deleteUserProfileFields(@PathVariable int userid, @RequestBody List<UserMetaDto> profile) {
        service.deleteByUserIdAndMetaKey(userid, profile);
    }

}