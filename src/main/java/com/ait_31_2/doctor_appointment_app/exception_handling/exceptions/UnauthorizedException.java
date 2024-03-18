package com.ait_31_2.doctor_appointment_app.exception_handling.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
