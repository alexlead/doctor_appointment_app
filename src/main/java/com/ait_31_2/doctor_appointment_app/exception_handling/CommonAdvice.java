package com.ait_31_2.doctor_appointment_app.exception_handling;

import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handling advice for the application.
 * Handles exceptions thrown by services and provides a consistent response format.
 */
@ControllerAdvice
public class CommonAdvice {

    /**
     * Exception handler for when attempting to create a user that already exists.
     * @param e The exception thrown when attempting to create an existing user.
     * @return A response with an error message and HTTP status 400 (Bad Request).
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Response> handleException(UserAlreadyExistsException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Exception handler for unauthorized access attempts.
     * @param e The exception representing unauthorized access.
     * @return A response with an error message and HTTP status 400 (Bad Request).
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Response> handleException(UnauthorizedException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for when a doctor is not found.
     * @param e The exception representing a doctor not being found.
     * @return A response with an error message and HTTP status 400 (Bad Request).
     */
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Response> handleException(DoctorNotFoundException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Exception handler for when no free slots are available.
     * @param e The exception representing no free slots available.
     * @return A response with an error message and HTTP status 400 (Bad Request).
     */
    @ExceptionHandler(NotFreeSlotsException.class)
    public ResponseEntity<Response> handleException(NotFreeSlotsException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /**
     * Exception handler for when an appointment is not found.
     * @param e The exception representing an appointment not being found.
     * @return A response with an error message and HTTP status 400 (Bad Request).
     */
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Response> handleException(AppointmentNotFoundException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for access denied situations.
     * @param e The exception representing access being denied.
     * @return A response with an error message and HTTP status 400 (Bad Request).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleException(AccessDeniedException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for when a user is not found.
     * @param e The exception representing a user not being found.
     * @return A response with an error message and HTTP status 400 (Bad Request).
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleException(UserNotFoundException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    /**
     * Exception handler for when a slot is not found.
     * @param e The exception representing a slot not being found.
     * @return A response with an error message and HTTP status 400 (Bad Request).
     */
    @ExceptionHandler(SlotNotFoundException.class)
    public ResponseEntity<Response> handleException(SlotNotFoundException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<Response> handleException(TokenRefreshException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
