package com.ait_31_2.doctor_appointment_app.exception_handling.exceptions;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String message) {
        super(message);
    }
}

