package com.ait_31_2.doctor_appointment_app.services.mapping;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentMappingService {
    @Autowired
    UserMappingService userMappingService;


    public AppointmentDto mapEntityToDto(Appointment appointment) {
        User userId = (appointment.getDoctorId());
        UserDto doctorId = userMappingService.mapUserToDtoName(userId);

        return new AppointmentDto(appointment.getId(),null, doctorId, appointment.getSlotId(),
                appointment.getDate(), appointment.isVisitComplete());


    }
}
