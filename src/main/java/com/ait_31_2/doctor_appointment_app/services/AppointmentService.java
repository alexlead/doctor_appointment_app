package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.repositories.AppointmentRepository;
import com.ait_31_2.doctor_appointment_app.services.mapping.AppointmentMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class AppointmentService {

    AppointmentRepository repository;
    AppointmentMappingService appointmentMappingService;

    public AppointmentService(AppointmentRepository repository, AppointmentMappingService appointmentMappingService) {
        this.repository = repository;
        this.appointmentMappingService = appointmentMappingService;
    }

    public List<AppointmentDto> getAllAppointmentsPatient(int patientId, Date timeStart, Date timeEnd) {
        return repository.findAllAppointmentsPatientByDataInterval(patientId, timeStart, timeEnd)
                .stream()
                .map(a -> appointmentMappingService.mapEntityToDto(a))
                .toList();

    }


}
