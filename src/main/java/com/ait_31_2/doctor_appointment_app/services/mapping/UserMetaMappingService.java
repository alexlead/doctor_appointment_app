package com.ait_31_2.doctor_appointment_app.services.mapping;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.classes.UserMeta;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserMetaDto;
import org.springframework.stereotype.Service;
/**
 * Service class for mapping between UserMeta entities and UserMetaDto objects.
 *
 * @author Alexandr
 * @version 1.0.0
 */
@Service
public class UserMetaMappingService {

    /**
     * Maps a {@link UserMeta} entity to a {@link UserMetaDto} object.
     *
     * @param userMeta The UserMeta entity to map.
     * @return The mapped UserMetaDto object.
     */
    public UserMetaDto mapUserMetaToDto(UserMeta userMeta) {
        String key = userMeta.getMetaKey();
        String value = userMeta.getMetaValue();
        return new UserMetaDto(key, value);
    }

    /**
     * Maps a {@link UserMetaDto} object to a {@link UserMeta} entity.
     *
     * @param dto The UserMetaDto object to map.
     * @param user The associated User entity.
     * @return The mapped UserMeta entity.
     */
    public UserMeta mapDtoToEntity(UserMetaDto dto, User user) {
        int id = 0;
        String key = dto.getMetaKey();
        String value = dto.getMetaValue();
        return new UserMeta(id, user, key, value);
    }
}
