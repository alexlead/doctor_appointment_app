package com.ait_31_2.doctor_appointment_app.exception_handling.exceptions;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
