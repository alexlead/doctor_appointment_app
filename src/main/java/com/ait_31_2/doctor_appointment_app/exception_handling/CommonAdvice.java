package com.ait_31_2.doctor_appointment_app.exception_handling;

import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Response> handleException(UserAlreadyExistsException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Response> handleException(UnauthorizedException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Response> handleException(DoctorNotFoundException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(NotFreeSlotsException.class)
    public ResponseEntity<Response> handleException(NotFreeSlotsException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Response> handleException(AppointmentNotFoundException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> handleException(AccessDeniedException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
