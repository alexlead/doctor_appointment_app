package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.repositories.AppointmentRepository;
import com.ait_31_2.doctor_appointment_app.repositories.RoleRepository;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import com.ait_31_2.doctor_appointment_app.repositories.UserRepository;
import com.ait_31_2.doctor_appointment_app.security.security_dto.AuthInfo;
import com.ait_31_2.doctor_appointment_app.services.mapping.AppointmentMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMappingService appointmentMappingService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SlotRepository slotRepository;


    public List<AppointmentDto> getAllAppointmentsPatient(LocalDate timeStart, LocalDate timeEnd) {
        if (timeStart.isAfter(timeEnd)) {
            throw new IllegalArgumentException("The start time must not be later than the end time!");
        }
        int patientId = getUserId();
        return repository.findAllAppointmentsPatientByDataInterval(patientId, timeStart, timeEnd)
                .stream()
                .map(a -> appointmentMappingService.mapEntityToDto(a))
                .toList();

    }

    public List<AppointmentDto> getFutureAppointmentsPatient(int patientId) {
        return repository.findFutureAppointments(patientId)
                .stream()
                .map(a -> appointmentMappingService.mapEntityToDto(a))
                .toList();

    }


    public List<AppointmentDto> getPastAppointmentsPatient(int patientId) {
        return repository.findPastAppointments(patientId)
                .stream()
                .filter(appointment -> appointment.isVisitComplete())
                .map(a -> appointmentMappingService.mapEntityToDto(a))
                .toList();

    }


    public int saveNewAppointment(LocalDate date, int userId1, int slotId) {

        int userId2 = getUserId();
        Appointment newAppointment = new Appointment();

        newAppointment.setDate(date);

        Slot slot = slotRepository.findById(slotId).orElse(null);
        newAppointment.setSlotId(slot);
        newAppointment.setVisitComplete(true);


        User user1 = userRepository.findById(userId1).orElse(null);
        if (user1 != null) {
            if (hasRole(user1, "ROLE_DOCTOR")) {
                newAppointment.setDoctorId(user1);
            } else if (hasRole(user1, "ROLE_PATIENT")) {
                newAppointment.setPatientId(user1);
            }
        }

        User user2 = userRepository.findById(userId2).orElse(null);
        if (user2 != null) {
            if (hasRole(user2, "ROLE_PATIENT")) {
                newAppointment.setPatientId(user2);
            } else if (hasRole(user2, "ROLE_DOCTOR")) {
                newAppointment.setDoctorId(user2);
            }
        }


        Appointment saveAppointment = repository.save(newAppointment);

        return saveAppointment.getId();
    }

    private int getUserId() {
        AuthInfo auth = (AuthInfo) SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userRepository.findByUsername(username);

        return user.getId();

    }

    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals(roleName));
    }


}
