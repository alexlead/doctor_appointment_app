package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.RegistrationForm;
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
    void getAllUser() {
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

        // Мокируем метод findAllByRole для UserRepository,
        // чтобы возвращать фиктивные данные о врачах
        when(userRepository.findAllByRole("ROLE_DOCTOR")).thenReturn(Arrays.asList(doctor1, doctor2));

        // Мокируем метод mapUserToDtoName для UserMappingService,
        // чтобы возвращать фиктивные объекты UserDto
        when(userMappingService.mapUserToDtoName(doctor1)).thenReturn(new UserDto(1, "Doctor1", "Surname1", null, null));
        when(userMappingService.mapUserToDtoName(doctor2)).thenReturn(new UserDto(2, "Doctor2", "Surname2", null, null));

        // Вызываем метод, который мы тестируем
        List<UserDto> doctors = userService.getAllDoctors();

        // Проверяем ожидаемый результат
        assertEquals(2, doctors.size());
        assertEquals("Doctor1", doctors.get(0).getName());
        assertEquals("Surname1", doctors.get(0).getSurname());
        assertEquals("Doctor2", doctors.get(1).getName());
        assertEquals("Surname2", doctors.get(1).getSurname());
    }

    @Test
    void getAllDoctorsWithPhoto() {
    }

    @Test
    void getDoctorById() {
    }

    @Test
    void getPatientByName() {
    }

    @Test
    void getUserByRole() {
    }

    @Test
    void getUserById() {
    }
}