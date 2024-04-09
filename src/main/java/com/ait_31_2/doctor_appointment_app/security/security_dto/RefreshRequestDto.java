package com.ait_31_2.doctor_appointment_app.security.security_dto;

import java.util.Objects;

/**
 * Data transfer object (DTO) representing a request to refresh an access token.
 */
public class RefreshRequestDto {

    private String refreshToken;

    /**
     * Constructs a new RefreshRequestDto with the specified refresh token.
     *
     * @param refreshToken The refresh token.
     */
    public RefreshRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
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
