package com.ait_31_2.doctor_appointment_app.controllers;

import com.ait_31_2.doctor_appointment_app.domain.dto.UserMetaDto;
import com.ait_31_2.doctor_appointment_app.services.UserMetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing user profile metadata.
 * Handles requests related to user profiles.
 *
 * @author Alexandr
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/profile")
@Tag(name = "User Profile Metadata Controller", description = "Endpoints for managing user profile metadata")
public class UserMetaController {
    /**
     * @see UserMetaService for handling user profile metadata operations.
     */
    private UserMetaService service;

    public UserMetaController(UserMetaService service) {
        this.service = service;
    }

    /**
     * Retrieves the user profile metadata for the current user.
     *
     * @return a list of {@link UserMetaDto} objects representing user profile metadata.
     */
    @GetMapping
    @Operation(summary = "Retrieve User Profile Metadata",
            description = "Retrieves the user profile metadata for the current user. Available to registered users.")
    public List<UserMetaDto> getUserProfileById() {
        return service.getUserProfileById();
    }

    /**
     * Retrieves the user profile metadata for the authenticated user.
     *
     * @param profile List of UserMetaDto objects containing metadata fields to be deleted.
     */
    @PutMapping
    @Operation(summary = "Update User Profile Metadata",
            description = "Updates the user profile metadata for the current user.")
    public void updateUserProfileById(@RequestBody List<UserMetaDto> profile) {
        service.updateUserProfileById(profile);
    }

    /**
     * Deletes specific user profile metadata fields for the authenticated user.
     *
     * @param profile List of UserMetaDto objects containing metadata fields to be deleted.
     */
    @DeleteMapping
    @Operation(summary = "Delete User Profile Metadata",
            description = "Deletes specific user profile metadata fields for the current user")
    public void deleteUserProfileFields(@RequestBody List<UserMetaDto> profile) {
        service.deleteByUserIdAndMetaKey(profile);
    }

}