package com.ait_31_2.doctor_appointment_app.exception_handling.exceptions;

public class SlotNotFoundException extends RuntimeException{
    public SlotNotFoundException(String message) {
        super(message);
    }
}
