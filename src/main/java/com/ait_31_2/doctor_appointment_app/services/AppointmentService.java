package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.repositories.AppointmentRepository;
import com.ait_31_2.doctor_appointment_app.services.mapping.UserMappingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

    AppointmentRepository repository;
    UserService userService;
    @Autowired
    UserMappingService mapping;

    public AppointmentService(AppointmentRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }


    public List<Slot> getAllFreeSlotByDateAndDoctor(Date date, String doctorName, String doctorSurname) {
        UserDto doctor = userService.getDoctorByName(doctorName, doctorSurname);
        int doctorId = mapping.mapUserDtoToEntityUser(doctor).getId();
        return repository.findFreeSlotsByDateAndDoctor(date, doctorId);
    }
}
