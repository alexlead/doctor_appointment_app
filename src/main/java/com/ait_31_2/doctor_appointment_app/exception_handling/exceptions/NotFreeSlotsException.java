package com.ait_31_2.doctor_appointment_app.exception_handling.exceptions;

public class NotFreeSlotsException extends RuntimeException {
    public NotFreeSlotsException(String message) {
        super(message);
    }
}
