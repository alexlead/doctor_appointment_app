package com.ait_31_2.doctor_appointment_app.exception_handling;

import java.util.Objects;
/**
 * Represents a response object with a status and a message.
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response response)) return false;
        return Objects.equals(getStatus(), response.getStatus()) && Objects.equals(getMessage(), response.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getMessage());
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

