package com.ait_31_2.doctor_appointment_app.security.security_dto;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

/**
 * Data transfer object (DTO) representing a request to refresh an access token.
 */
@Entity
@Table(name = "refreshtoken")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    public RefreshToken() {
    }

    /**
     * Constructs a new RefreshRequestDto with the specified refresh token.
     *
     * @param refreshToken The refresh token.
     */
    public RefreshToken(long id, User user, String refreshToken, Instant expiryDate) {
        this.id = id;
        this.user = user;
        this.token = refreshToken;
        this.expiryDate = expiryDate;
    }

    public RefreshToken(User user, String refreshToken, Instant expiryDate) {
        this.user = user;
        this.token = refreshToken;
        this.expiryDate = expiryDate;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefreshToken that)) return false;
        return getId() == that.getId() && Objects.equals(getUser(), that.getUser()) && Objects.equals(getToken(), that.getToken()) && Objects.equals(getExpiryDate(), that.getExpiryDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getToken(), getExpiryDate());
    }

    @Override
    public String toString() {
        return "RefreshRequestDto{" +
                "id=" + id +
                ", user=" + user +
                ", refreshToken='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
