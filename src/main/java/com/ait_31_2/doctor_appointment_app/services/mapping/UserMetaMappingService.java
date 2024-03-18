package com.ait_31_2.doctor_appointment_app.services.mapping;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.classes.UserMeta;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserMetaDto;
import org.springframework.stereotype.Service;

@Service
public class UserMetaMappingService {

    public UserMetaDto mapUserMetaToDto(UserMeta userMeta) {
        String key = userMeta.getMetaKey();
        String value = userMeta.getMetaValue();
        return new UserMetaDto(key, value);
    }

    public UserMeta mapDtoToEntity(UserMetaDto dto, User user) {
        int id = 0;
        String key = dto.getMetaKey();
        String value = dto.getMetaValue();
        return new UserMeta(id, user, key, value);
    }
}
