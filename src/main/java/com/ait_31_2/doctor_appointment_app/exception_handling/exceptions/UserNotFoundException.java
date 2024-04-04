package com.ait_31_2.doctor_appointment_app.exception_handling.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
