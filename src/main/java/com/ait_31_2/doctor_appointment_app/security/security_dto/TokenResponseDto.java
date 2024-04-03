package com.ait_31_2.doctor_appointment_app.security.security_dto;

import java.util.Objects;

public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private String message;

    public TokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenResponseDto(String accessToken, String refreshToken, String message) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
    }

    public TokenResponseDto(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenResponseDto that)) return false;
        return Objects.equals(getAccessToken(), that.getAccessToken()) && Objects.equals(getRefreshToken(), that.getRefreshToken()) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccessToken(), getRefreshToken(), getMessage());
    }

    @Override
    public String toString() {
        return "TokenResponseDto{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
