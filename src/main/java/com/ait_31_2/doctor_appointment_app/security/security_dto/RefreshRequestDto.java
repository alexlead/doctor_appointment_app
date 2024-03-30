package com.ait_31_2.doctor_appointment_app.security.security_dto;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class RefreshRequestDto {

    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public RefreshRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefreshRequestDto that)) return false;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken);
    }

    @Override
    public String toString() {
        return "RefreshRequestDto{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
