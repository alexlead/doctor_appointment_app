package com.ait_31_2.doctor_appointment_app.exception_handling;

import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserAlreadyExistsException;

import com.ait_31_2.doctor_appointment_app.exception_handling.responses.UserSuccessRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Response> handleException(UserAlreadyExistsException e) {
        e.printStackTrace();
        Response response = new Response(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserSuccessRegistration.class)
    public ResponseEntity<Response> handleException(UserSuccessRegistration e) {
        e.printStackTrace();
        Response response = new Response(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
