package com.ait_31_2.doctor_appointment_app.security.security_service;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.TokenRefreshException;
import com.ait_31_2.doctor_appointment_app.repositories.RoleRepository;
import com.ait_31_2.doctor_appointment_app.security.repositories.RefreshTokenRepository;
import com.ait_31_2.doctor_appointment_app.security.security_dto.AuthInfo;
import com.ait_31_2.doctor_appointment_app.security.security_dto.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Service class for managing authentication tokens.
 * Handles token generation, validation, and retrieval of token claims.
 */
@Service
public class TokenService {
    private SecretKey accessKey;
    private SecretKey refreshKey;
    private RoleRepository roleRepository;
    @Autowired
    private RefreshTokenRepository tokenRepository;

    /**
     * Constructs an instance of TokenService with the specified dependencies.
     *
     * @param accessKey      The access key used for generating access tokens.
     * @param refreshKey     The refresh key used for generating refresh tokens.
     * @param roleRepository The {@link RoleRepository} instance for retrieving user roles.
     */
    public TokenService(@Value("${access.key}") String accessKey,
                        @Value("${refresh.key}") String refreshKey,
                        RoleRepository roleRepository) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
        this.roleRepository = roleRepository;

    }

    /**
     * Generates an access token for the specified user.
     *
     * @param user The {@link User} for whom the access token is generated.
     * @return The generated access token.
     */
    public String generateAccessToken(@Nonnull User user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expirationInstant = currentDate.plusHours(1).atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim("roles", user.getRoles())
                .claim("username", user.getUsername())
                .claim("name", user.getName())
                .claim("surname", user.getSurname())
                .compact();
    }

    /**
     * Generates a refresh token for the specified user.
     *
     * @param user The {@link User} for whom the refresh token is generated.
     * @return The generated refresh token.
     */
    public RefreshToken generateRefreshToken(@Nonnull User user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expirationInstant = currentDate.plusDays(7).atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(expirationInstant);

        String token = Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();

        RefreshToken refreshToken = new RefreshToken(user, token, expirationInstant);
        return tokenRepository.save(refreshToken);

    }

    /**
     * Validates an access token.
     *
     * @param accessToken The access token to validate.
     * @return True if the access token is valid, false otherwise.
     */
    public boolean validateAccessToken(@Nonnull String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    /**
     * Validates a refresh token.
     *
     * @param refreshToken The refresh token to validate.
     * @return True if the refresh token is valid, false otherwise.
     */
    public boolean validateRefreshToken(@Nonnull String refreshToken) {

        if (!(validateToken(refreshToken, refreshKey))) {
            tokenRepository.findByToken(refreshToken)
                    .ifPresent(tokenRepository::delete);
            throw new TokenRefreshException("Refresh token was expired. Please make a new signin request");
        }
        return true;
    }

    /**
     * Validates a  token, universal method
     *
     * @param token The refresh token to validate.
     * @param key   The secret key to validate.
     * @return True if the  token is valid, false otherwise.
     */
    private boolean validateToken(@Nonnull String token, @Nonnull SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieves claims from an access token.
     *
     * @param accessToken The access token from which to retrieve claims.
     * @return The claims extracted from the access token.
     */
    public Claims getAccessClaims(@Nonnull String accessToken) {
        return getClaims(accessToken, accessKey);
    }

    /**
     * Retrieves claims from a refresh token.
     *
     * @param refreshToken The refresh token from which to retrieve claims.
     * @return The claims extracted from the refresh token.
     */
    public Claims getRefreshClaims(@Nonnull String refreshToken) {
        return getClaims(refreshToken, accessKey);
    }

    /**
     * Retrieves claims from a  token.
     *
     * @param token The refresh token to validate.
     * @param key   The secret key to validate.
     * @return The claims extracted from the token.
     */
    private Claims getClaims(@Nonnull String token, @Nonnull SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates authentication information based on token claims.
     *
     * @param claims The claims extracted from a token.
     * @return The AuthInfo object containing authentication details.
     */
    public AuthInfo generateAuthInfo(Claims claims) {
        String username = claims.getSubject();
        String name = claims.get("name", String.class);
        String surname = claims.get("surname", String.class);


        List<LinkedHashMap<String, String>> rolesList = (List<LinkedHashMap<String, String>>) claims.get("roles");
        Set<Role> roles = new HashSet<>();

        for (LinkedHashMap<String, String> roleEntry : rolesList) {
            String roleName = roleEntry.get("authority");
            Role role = roleRepository.findByName(roleName);
            roles.add(role);
        }


        return new AuthInfo(username, name, surname, roles);
    }

}

