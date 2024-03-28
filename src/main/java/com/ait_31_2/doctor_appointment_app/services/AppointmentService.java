package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.repositories.AppointmentRepository;
import com.ait_31_2.doctor_appointment_app.services.mapping.AppointmentMappingService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    AppointmentRepository repository;
    AppointmentMappingService appointmentMappingService;

    public AppointmentService(AppointmentRepository repository, AppointmentMappingService appointmentMappingService) {
        this.repository = repository;
        this.appointmentMappingService = appointmentMappingService;
    }

    public List<AppointmentDto> getAllAppointmentsPatient(int patientId, LocalDate timeStart, LocalDate timeEnd) {
        return repository.findAllAppointmentsPatientByDataInterval(patientId, timeStart, timeEnd)
                .stream()
                .map(a -> appointmentMappingService.mapEntityToDto(a))
                .toList();

    }

    public List<AppointmentDto> getFutureAppointmentsPatient(int patientId){
        return repository.findFutureAppointments(patientId)
                .stream()
                .map(a->appointmentMappingService.mapEntityToDto(a))
                .toList();

    }

    public List<AppointmentDto> getPastAppointmentsPatient(int patientId){
        return repository.findPastAppointments(patientId)
                .stream()
                .filter(appointment -> appointment.isVisitComplete())
                .map(a->appointmentMappingService.mapEntityToDto(a))
                .toList();

    }


}
