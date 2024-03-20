package com.ait_31_2.doctor_appointment_app.exception_handling;

import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.DoctorNotFoundException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UnauthorizedException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserAlreadyExistsException;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.UserNotFoundException;
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleException(UserNotFoundException e) {
        e.printStackTrace();
        Response response = new Response("ERROR",e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
