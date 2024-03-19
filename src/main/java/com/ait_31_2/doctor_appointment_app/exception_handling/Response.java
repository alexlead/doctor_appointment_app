package com.ait_31_2.doctor_appointment_app.exception_handling;

public class Response {
    private String status;
    private String message;


    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

