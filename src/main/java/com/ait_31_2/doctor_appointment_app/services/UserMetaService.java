package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.classes.UserMeta;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserMetaDto;
import com.ait_31_2.doctor_appointment_app.repositories.UserMetaRepository;
import com.ait_31_2.doctor_appointment_app.services.mapping.UserMetaMappingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMetaService {
    private UserMetaRepository repository;
    private UserMetaMappingService mapping;


    public UserMetaService(UserMetaRepository repository, UserMetaMappingService mapping) {
        this.repository = repository;
        this.mapping = mapping;
    }

    public List<UserMetaDto> getUserProfileById(int userId) {

        List<UserMetaDto> userProfile = repository.findAllByUserId(userId)
                .stream()
                .map(userMeta -> mapping.mapUserMetaToDto(userMeta))
                .toList();

        if (userProfile.isEmpty()) {
            return null;
        }
        return userProfile;
    }

    @Transactional
    public void updateUserProfileById(int userId, List<UserMetaDto> profile) {
        User user = new User();
        user.setId(userId);
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

    public void deleteByUserIdAndMetaKey(int userId, List<UserMetaDto> profile) {
        for (UserMetaDto dto : profile) {
            repository.deleteByUserIdAndMetaKey(userId, dto.getMetaKey());
        }
    }


}
