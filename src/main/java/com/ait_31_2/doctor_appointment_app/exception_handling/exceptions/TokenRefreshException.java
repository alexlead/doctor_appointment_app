package com.ait_31_2.doctor_appointment_app.exception_handling.exceptions;

public class TokenRefreshException extends RuntimeException{
    public TokenRefreshException(String message) {
        super(message);
    }
}
