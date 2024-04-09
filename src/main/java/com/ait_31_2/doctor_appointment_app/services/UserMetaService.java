package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.classes.UserMeta;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserMetaDto;
import com.ait_31_2.doctor_appointment_app.repositories.UserMetaRepository;
import com.ait_31_2.doctor_appointment_app.repositories.UserRepository;
import com.ait_31_2.doctor_appointment_app.services.mapping.UserMetaMappingService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing user metadata.
 *
 * @author Alexandr
 * @version 1.1.0
 */
@Service
public class UserMetaService {
    private UserMetaRepository repository;
    private UserRepository userRepository;
    private UserMetaMappingService mapping;

    /**
     * Constructs a new UserMetaService with the specified repositories and mapping service.
     *
     * @param repository     the {@link UserMetaRepository} for user metadata
     * @param mapping        the {@link UserMetaMappingService} service for user metadata DTOs
     * @param userRepository the {@link UserRepository} for user entities
     */
    public UserMetaService(UserMetaRepository repository, UserMetaMappingService mapping, UserRepository userRepository) {
        this.repository = repository;
        this.mapping = mapping;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the current authenticated user.
     *
     * @return the current authenticated user
     */
    public User getCurrentUser() {
        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
        String username = String.valueOf(authenticationToken.getPrincipal());
        User user = userRepository.findByUsername(username);
        return user;
    }

    /**
     * Retrieves the user profile based on the current authenticated {@link User}.
     *
     * @return the user profile as a list of {@link UserMetaDto} objects
     */
    public List<UserMetaDto> getUserProfileById() {

        User user = getCurrentUser();

        List<UserMetaDto> userProfile = repository.findAllByUserId(user.getId())
                .stream()
                .map(userMeta -> mapping.mapUserMetaToDto(userMeta))
                .toList();

        if (userProfile.isEmpty()) {
            return null;
        }
        return userProfile;
    }

    /**
     * Updates the user profile based on the provided list of UserMetaDto objects.
     *
     * @param profile the list of {@link UserMetaDto} objects containing the updated user profile
     */
    @Transactional
    public void updateUserProfileById(List<UserMetaDto> profile) {
        User user = getCurrentUser();
        for (UserMetaDto dto : profile) {
            UserMeta meta = repository.findByUserIdAndMetaKey(user.getId(), dto.getMetaKey());
            if (meta == null) {
                meta = new UserMeta(0, user, dto.getMetaKey(), dto.getMetaValue());
                repository.save(meta);
            } else {
                if (dto.getMetaValue().length() > 0) {
                    meta.setMetaValue(dto.getMetaValue());
                    repository.save(meta);
                } else {
                    repository.deleteByUserIdAndMetaKey(user.getId(), dto.getMetaKey());
                }
            }
        }

    }

    /**
     * Deletes user metadata based on the provided list of {@link UserMetaDto} objects.
     *
     * @param profile the list of UserMetaDto objects containing the metadata to delete
     */
    public void deleteByUserIdAndMetaKey(List<UserMetaDto> profile) {
        User user = getCurrentUser();
        for (UserMetaDto dto : profile) {
            repository.deleteByUserIdAndMetaKey(user.getId(), dto.getMetaKey());
        }
    }

}
