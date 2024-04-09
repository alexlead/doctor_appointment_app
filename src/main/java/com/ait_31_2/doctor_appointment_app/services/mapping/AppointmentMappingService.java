package com.ait_31_2.doctor_appointment_app.services.mapping;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for mapping appointment entities to DTOs and vice versa.
 */
@Service
public class AppointmentMappingService {

    /**
     * {@link UserMetaMappingService} for mapping user entities to DTOs.
     */
    @Autowired
    UserMappingService userMappingService;

    /**
     * Maps an appointment entity to a DTO for patient view.
     *
     * @param appointment The appointment entity to map.
     * @return An AppointmentDto for patient view.
     */
    public AppointmentDto mapEntityToDtoPatient(Appointment appointment) {
        User userId = (appointment.getDoctorId());
        UserDto doctorId = userMappingService.mapUserToDtoName(userId);

        return new AppointmentDto(appointment.getId(),null, doctorId, appointment.getSlotId(),
                appointment.getDate(), appointment.isVisitComplete());

    }

    /**
     * Maps an appointment entity to a DTO for doctor view.
     *
     * @param appointment The appointment entity to map.
     * @return An AppointmentDto for doctor view.
     */
    public AppointmentDto mapEntityToDtoDoctor(Appointment appointment) {
        User userId = (appointment.getPatientId());
        UserDto patientId = userMappingService.mapUserToDtoName(userId);

        return new AppointmentDto(appointment.getId(),patientId, null, appointment.getSlotId(),
                appointment.getDate(), appointment.isVisitComplete());

    }


    /**
     * Maps an appointment entity to a DTO.
     *
     * @param appointment The appointment entity to map.
     * @return An AppointmentDto.
     */
    public AppointmentDto mapAppointmentToDto(Appointment appointment){
        UserDto patientId = userMappingService.mapUserToDtoName(appointment.getPatientId());
        UserDto doctorId = userMappingService.mapUserToDtoName(appointment.getDoctorId());
        return new AppointmentDto(appointment.getId(),patientId,doctorId,appointment.getSlotId(),appointment.getDate(), true);
    }
}
