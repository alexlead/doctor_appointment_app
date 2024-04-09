package com.ait_31_2.doctor_appointment_app.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * DTO class representing metadata associated with a user.
 * Contains information about a specific metadata key-value pair.
 *
 * @author Alexandr
 * @version 1.0.0
 */
@Schema(description = "DTO class representing metadata associated with a user")
public class UserMetaDto {
    @Schema(description = "The metadata key")
    private String metaKey;
    @Schema(description = "The metadata value")
    private String metaValue;

    /**
     * Constructs a new UserMetaDto object with the specified metadata key-value pair.
     *
     * @param metaKey   the metadata key
     * @param metaValue the metadata value
     */
    public UserMetaDto(String metaKey, String metaValue) {
        this.metaKey = metaKey;
        this.metaValue = metaValue;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMetaDto that = (UserMetaDto) o;
        return Objects.equals(metaKey, that.metaKey) && Objects.equals(metaValue, that.metaValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaKey, metaValue);
    }

    @Override
    public String toString() {
        return "UserMetaDto{" +
                "metaKey='" + metaKey + '\'' +
                ", metaValue='" + metaValue + '\'' +
                '}';
    }
}
