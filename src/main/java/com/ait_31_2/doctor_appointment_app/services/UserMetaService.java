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

@Service
public class UserMetaService {
    private UserMetaRepository repository;
    private UserRepository userRepository;
    private UserMetaMappingService mapping;


    public UserMetaService(UserMetaRepository repository, UserMetaMappingService mapping, UserRepository userRepository) {
        this.repository = repository;
        this.mapping = mapping;
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
        String username = String.valueOf(authenticationToken.getPrincipal());
        User user = userRepository.findByUsername(username);
        return user;
    }

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

    @Transactional
    public void updateUserProfileById( List<UserMetaDto> profile) {
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

    public void deleteByUserIdAndMetaKey(List<UserMetaDto> profile) {
        User user = getCurrentUser();
        for (UserMetaDto dto : profile) {
            repository.deleteByUserIdAndMetaKey(user.getId(), dto.getMetaKey());
        }
    }

}
