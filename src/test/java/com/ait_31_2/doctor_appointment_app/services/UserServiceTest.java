package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.RegistrationForm;
import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.UserDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserAlreadyExistsException;
import com.ait_31_2.doctor_appointment_app.repositories.UserRepository;
import com.ait_31_2.doctor_appointment_app.security.security_dto.TokenResponseDto;
import com.ait_31_2.doctor_appointment_app.security.security_service.TokenService;
import com.ait_31_2.doctor_appointment_app.services.mapping.UserMappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMappingService userMappingService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }




    @Test
    void testRegisterUser_UserAlreadyExists() {

        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setUsername("existingUsername");
        when(userRepository.findByUsername("existingUsername")).thenReturn(new User());


        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registrationForm));


        verify(userRepository, never()).save(any());
    }


    @Test
    void testRegisterUser_Success() {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setUsername("newUsername");
        User newUser = new User();
        when(userRepository.findByUsername("newUsername")).thenReturn(null);
        when(userMappingService.mapRegistrationFormToUser(registrationForm)).thenReturn(newUser);
        when(userRepository.save(newUser)).thenReturn(newUser);


        when(tokenService.generateAccessToken(newUser)).thenReturn("accessToken");
        when(tokenService.generateRefreshToken(newUser)).thenReturn("refreshToken");

        TokenResponseDto result = userService.registerUser(registrationForm);

        assertNotNull(result);
        assertEquals("accessToken", result.getAccessToken());
        assertEquals("refreshToken", result.getRefreshToken());
        assertEquals("User null null successfully registered!", result.getMessage());

        verify(userRepository, times(1)).save(newUser);
    }


    @Test
    void testGetAllUser() {

        User user1 = new User(1,"John", "Doe", "john@gmail.com", "password", null);
        User user2 = new User(2,"Jane", "Smith", "jane@gmail.com", "password", null);

        UserDto userDto1 = new UserDto(1, "John", "Doe", "john@gmail.com", null);
        UserDto userDto2 = new UserDto(2, "Jane", "Smith", "jane@gmail.com", null);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        when(userMappingService.mapUserToDto(user1)).thenReturn(userDto1);
        when(userMappingService.mapUserToDto(user2)).thenReturn(userDto2);

        List<UserDto> result = userService.getAllUser();

        assertEquals(2, result.size());
        assertEquals(userDto1, result.get(0));
        assertEquals(userDto2, result.get(1));
    }


    @Test
    void testGetAllDoctors() {

        User doctor1 = new User();
        doctor1.setId(1);
        doctor1.setName("Doctor1");
        doctor1.setSurname("Surname1");

        User doctor2 = new User();
        doctor2.setId(2);
        doctor2.setName("Doctor2");
        doctor2.setSurname("Surname2");

        when(userRepository.findAllByRole("ROLE_DOCTOR")).thenReturn(Arrays.asList(doctor1, doctor2));

        when(userMappingService.mapUserToDtoName(doctor1)).thenReturn(new UserDto(1, "Doctor1", "Surname1", null, null));
        when(userMappingService.mapUserToDtoName(doctor2)).thenReturn(new UserDto(2, "Doctor2", "Surname2", null, null));

        List<UserDto> doctors = userService.getAllDoctors();

        assertEquals(2, doctors.size());
        assertEquals("Doctor1", doctors.get(0).getName());
        assertEquals("Surname1", doctors.get(0).getSurname());
        assertEquals("Doctor2", doctors.get(1).getName());
        assertEquals("Surname2", doctors.get(1).getSurname());
    }





    @Test
    void testGetPatientByName() {
        List<User> patients = List.of(
                new User(1,"John", "Doe", "john@gmail.com", "password", Set.of(new Role(1,"ROLE_PATIENT"))),
                new User(2,"Jane", "Smith", "jane@gmail.com", "password", Set.of(new Role(1,"ROLE_PATIENT"))),
                new User(3, "Joseph", "Smith", "joseph@gmail.com", "password", Set.of(new Role(1, "ROLE_DOCTOR")))
        );

        String partName = "Sm";
        List<UserDto> expectedDtos = List.of(
                new UserDto(2, "Jane", "Smith", "jane@gmail.com",Set.of(new Role(1,"ROLE_PATIENT")))

        );

        when(userRepository.findUserByPartName(partName)).thenReturn(patients);
        when(userMappingService.mapUserToDto(patients.get(0))).thenReturn(expectedDtos.get(0));

        List<UserDto> result = userService.getPatientByName(partName);

        assertEquals(2, result.size());
        assertEquals(expectedDtos.get(0), result.get(0));
    }


}