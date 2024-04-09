package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.repositories.AppointmentRepository;
import com.ait_31_2.doctor_appointment_app.repositories.RoleRepository;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import com.ait_31_2.doctor_appointment_app.repositories.UserRepository;
import com.ait_31_2.doctor_appointment_app.security.security_dto.AuthInfo;
import com.ait_31_2.doctor_appointment_app.services.mapping.AppointmentMappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {
    @Mock
    private AppointmentRepository repository;
    @Mock
    private AppointmentMappingService appointmentMappingService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SlotRepository slotRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication auth;
    @Mock
    private AuthInfo authInfo;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAppointments() {

    }

    @Test
    void getFutureAppointmentsPatient() {
    }

    @Test
    void getPastAppointmentsPatient() {
    }

    @Test
    void getAppointmentById() {
    }

    @Test
    void saveAppointment() {
    }

    @Test
    void deleteById() {
    }
}