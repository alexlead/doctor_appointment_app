package com.ait_31_2.doctor_appointment_app.exception_handling;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
