package com.ait_31_2.doctor_appointment_app.security.repositories;

import com.ait_31_2.doctor_appointment_app.security.security_dto.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);
}
