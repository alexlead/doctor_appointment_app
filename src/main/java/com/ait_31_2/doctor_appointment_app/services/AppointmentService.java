package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.AppointmentRequest;
import com.ait_31_2.doctor_appointment_app.domain.classes.Appointment;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.AccessDeniedException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.AppointmentNotFoundException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.SlotNotFoundException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserNotFoundException;
import com.ait_31_2.doctor_appointment_app.repositories.AppointmentRepository;
import com.ait_31_2.doctor_appointment_app.repositories.RoleRepository;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import com.ait_31_2.doctor_appointment_app.repositories.UserRepository;
import com.ait_31_2.doctor_appointment_app.security.security_dto.AuthInfo;
import com.ait_31_2.doctor_appointment_app.services.mapping.AppointmentMappingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing appointments.
 *
 * @author Tetiana
 */
@Service
@RequiredArgsConstructor
public class AppointmentService {
    /**
     * {@link AppointmentRepository} for accessing appointment data.
     */
    private final AppointmentRepository repository;
    /**
     * {@link AppointmentMappingService} for mapping appointment entities to DTOs.
     */
    private final AppointmentMappingService appointmentMappingService;
    /**
     * {@link RoleRepository} for accessing role data.
     */
    private final RoleRepository roleRepository;
    /**
     * {@link UserRepository} for accessing user data.
     */
    private final UserRepository userRepository;
    /**
     * {@link SlotRepository} for accessing slot data.
     */
    private final SlotRepository slotRepository;

    /**
     * Retrieves a list of appointments within the specified time interval.
     * * If the start time is later than the end time, an IllegalArgumentException is thrown.
     * * If the user does not have a valid role ("ROLE_DOCTOR" or "ROLE_PATIENT"), an IllegalArgumentException is thrown.
     * * For patients, appointments are filtered by the patient's ID and mapped to DTOs using the patient mapping service.
     * * For doctors, appointments are filtered by the doctor's ID and mapped to DTOs using the doctor mapping service.
     *
     * @param timeStart the beginning of the time interval for which slots are shown.
     * @param timeEnd   the ending of the time interval for which slots are shown.
     * @return list {@link AppointmentDto} over a given time period
     * @throws IllegalArgumentException if the start time is later than the end time or if the user does not have a valid role.
     */
    @Transactional
    public List<AppointmentDto> getAllAppointments(LocalDate timeStart, LocalDate timeEnd) {
        if (timeStart.isAfter(timeEnd)) {
            throw new IllegalArgumentException("The start time must not be later than the end time!");
        }
        int userId = getUserId();
        User user = userRepository.findById(userId).orElse(null);

        if (!(hasRole(user, "ROLE_DOCTOR")) && !(hasRole(user, "ROLE_PATIENT"))) {
            throw new IllegalArgumentException("Invalid user role!");
        }
        if (hasRole(user, "ROLE_PATIENT")) {
            return repository.findAllAppointmentsPatientByDataInterval(userId, timeStart, timeEnd)
                    .stream()
                    .map(a -> appointmentMappingService.mapEntityToDtoPatient(a))
                    .toList();
        } else if (hasRole(user, "ROLE_DOCTOR")) {
            return repository.findAllAppointmentsDoctorByDataInterval(userId, timeStart, timeEnd)
                    .stream()
                    .map(a -> appointmentMappingService.mapEntityToDtoDoctor(a))
                    .toList();

        } else {
            throw new IllegalArgumentException("Invalid user role!");
        }

    }

    /**
     * Retrieves a list of future appointments {@link AppointmentDto} for the patient.
     * <p>
     * If the user is not found, a UserNotFoundException is thrown.
     * If the user does not have a valid role ("ROLE_DOCTOR" or "ROLE_PATIENT"), an IllegalArgumentException is thrown.
     * For patients, future appointments are filtered by the patient's ID and mapped to DTOs using the patient mapping service.
     * For doctors, future appointments are filtered by the doctor's ID and mapped to DTOs using the doctor mapping service.
     *
     * @return A list of future appointment DTOs for the patient.
     * @throws UserNotFoundException    if the user is not found.
     * @throws IllegalArgumentException if the user does not have a valid role.
     * @see AppointmentMappingService#mapEntityToDtoPatient(Appointment)
     * @see AppointmentMappingService#mapEntityToDtoDoctor(Appointment)
     */
    @Transactional
    public List<AppointmentDto> getFutureAppointmentsPatient() {
        int userId = getUserId();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }

        if (!(hasRole(user, "ROLE_DOCTOR")) && !(hasRole(user, "ROLE_PATIENT"))) {
            throw new IllegalArgumentException("Invalid user role!");
        }
        if (hasRole(user, "ROLE_PATIENT")) {
            return repository.findFutureAppointmentsPatient(userId)
                    .stream()
                    .map(a -> appointmentMappingService.mapEntityToDtoPatient(a))
                    .toList();
        } else if (hasRole(user, "ROLE_DOCTOR")) {
            return repository.findFutureAppointmentsDoctor(userId)
                    .stream()
                    .map(appointmentMappingService::mapEntityToDtoDoctor)
                    .toList();

        } else {
            throw new IllegalArgumentException("Invalid user role!");
        }
    }

    /**
     * Retrieves a list of past appointments for the patient.
     * <p>
     * If the user is not found, a UserNotFoundException is thrown.
     * If the user does not have the "ROLE_PATIENT" role, an IllegalArgumentException is thrown.
     * Past appointments are filtered by the patient's ID and mapped to DTOs using the {@link AppointmentMappingService#mapEntityToDtoPatient(Appointment)} method.
     *
     * @return A list of past appointment DTOs for the patient.
     * @throws UserNotFoundException    if the user is not found.
     * @throws IllegalArgumentException if the user does not have the "ROLE_PATIENT" role.
     * @see AppointmentMappingService#mapEntityToDtoPatient(Appointment)
     */
    @Transactional
    public List<AppointmentDto> getPastAppointmentsPatient() {
        int userId = getUserId();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }
        if (hasRole(user, "ROLE_PATIENT")) {
            return repository.findPastAppointments(userId)
                    .stream()
                    .map(u -> appointmentMappingService.mapEntityToDtoPatient(u))
                    .toList();
        } else {
            throw new IllegalArgumentException("Invalid user role!");
        }

    }

    /**
     * Retrieves an appointment by its ID.
     * <p>
     * If the appointment is not found, an AppointmentNotFoundException is thrown.
     * If the current user does not have access to the appointment (neither as a patient nor as a doctor), an AccessDeniedException is thrown.
     *
     * @param id The ID of the appointment to retrieve.
     * @return The appointment DTO if found and the user has access.
     * @throws AppointmentNotFoundException if the appointment with the specified ID is not found.
     * @throws AccessDeniedException        if the user does not have access to the appointment.
     */
    @Transactional
    public AppointmentDto getAppointmentById(int id) throws AccessDeniedException {
        Appointment appointment = repository.findById(id).orElse(null);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment with ID " + id + " not found.");
        }
        int userId = getUserId();
        User patientUser = appointment.getPatientId();
        User doctorUser = appointment.getDoctorId();
        if (userId == patientUser.getId() || userId == doctorUser.getId()) {

            return appointmentMappingService.mapAppointmentToDto(appointment);
        } else {
            throw new AccessDeniedException("Access denied!");
        }
    }

    /**
     * Saves a new appointment or updates an existing one based on the provided request.
     * <p>
     * If the appointment ID in the request is not 0 and an appointment with that ID exists, the existing appointment is updated with the new information.
     * If the appointment ID in the request is 0 or the appointment with the ID does not exist, a new appointment is created.
     * <p>
     * Only authorised users can create or modify their appointments.
     *
     * @param request The appointment request containing the necessary information.
     * @return The ID of the saved or updated appointment.
     * @throws UserNotFoundException if a user involved in the appointment (either doctor or patient) is not found.
     * @throws SlotNotFoundException if the slot specified in the request is not found.
     */
    @Transactional
    public int saveAppointment(AppointmentRequest request) {
        LocalDate date = request.getDate();
        int userId1 = request.getUserId1();
        int slotId = request.getSlotId();

        int userId2 = getUserId();

        int appointmentId = request.getAppointmentId();
        Appointment existingAppointment = repository.findById(appointmentId).orElse(null);

        if (appointmentId != 0 && existingAppointment != null) {
            existingAppointment.setDate(date);
            Slot slot = slotRepository.findById(slotId).orElse(null);
            existingAppointment.setSlotId(slot);
            existingAppointment.setVisitComplete(true);

            User user1 = userRepository.findById(userId1).orElse(null);
            if (user1 == null) {
                throw new UserNotFoundException("User not found!");
            }
            if (user1 != null) {
                if (hasRole(user1, "ROLE_DOCTOR")) {
                    existingAppointment.setDoctorId(user1);
                } else if (hasRole(user1, "ROLE_PATIENT")) {
                    existingAppointment.setPatientId(user1);
                }
            }

            User user2 = userRepository.findById(userId2).orElse(null);
            if (user2 == null) {
                throw new UserNotFoundException("User not found!");
            }
            if (user2 != null) {
                if (hasRole(user2, "ROLE_PATIENT")) {
                    existingAppointment.setPatientId(user2);
                } else if (hasRole(user2, "ROLE_DOCTOR")) {
                    existingAppointment.setDoctorId(user2);
                }
            }

            Appointment saveAppointment = repository.save(existingAppointment);
            return saveAppointment.getId();

        } else {
            Appointment newAppointment = new Appointment();

            newAppointment.setDate(date);

            Slot slot = slotRepository.findById(slotId).orElse(null);
            if (slot == null) {
                throw new SlotNotFoundException("Slot not found!");
            }
            newAppointment.setSlotId(slot);
            newAppointment.setVisitComplete(true);


            User user1 = userRepository.findById(userId1).orElse(null);
            if (user1 == null) {
                throw new UserNotFoundException("User not found!");
            }
            if (user1 != null) {
                if (hasRole(user1, "ROLE_DOCTOR")) {
                    newAppointment.setDoctorId(user1);
                } else if (hasRole(user1, "ROLE_PATIENT")) {
                    newAppointment.setPatientId(user1);
                }
            }

            User user2 = userRepository.findById(userId2).orElse(null);
            if (user2 == null) {
                throw new UserNotFoundException("User not found!");
            }
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
    }

    /**
     * Retrieves the ID of the currently authenticated user.
     *
     * @return The ID of the authenticated user.
     */
    private int getUserId() {
        AuthInfo auth = (AuthInfo) SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userRepository.findByUsername(username);

        return user.getId();

    }

    /**
     * Checks if the specified user has the given role.
     *
     * @param user     The user to check for the role.
     * @param roleName The name of the role to check.
     * @return true if the user has the specified role, otherwise false.
     */
    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getAuthority().equals(roleName));
    }

    /**
     * Deletes an appointment by its ID if the authenticated user has the necessary permissions.
     *
     * @param id The ID of the appointment to delete.
     * @throws AccessDeniedException        If the authenticated user does not have the necessary permissions.
     * @throws AppointmentNotFoundException If the appointment with the specified ID is not found.
     */
    @Transactional
    public void deleteById(int id) throws AccessDeniedException {
        int userId = getUserId();
        Appointment appointment = repository.findById(id).orElse(null);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment with ID " + id + " not found.");
        }

        User patientUser = appointment.getPatientId();
        User doctorUser = appointment.getDoctorId();
        if (userId == patientUser.getId() || userId == doctorUser.getId()) {
            repository.deleteById(id);

        } else {
            throw new AccessDeniedException("Access denied!");
        }

    }


}
