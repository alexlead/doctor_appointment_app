package com.ait_31_2.doctor_appointment_app.domain.dto;

import java.util.Objects;

public class UserMetaDto {
    private String metaKey;
    private String metaValue;

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
