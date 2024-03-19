package com.ait_31_2.doctor_appointment_app.exception_handling;

public class Response {
    protected String message;


    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

