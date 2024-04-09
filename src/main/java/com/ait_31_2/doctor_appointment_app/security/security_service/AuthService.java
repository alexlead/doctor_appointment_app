package com.ait_31_2.doctor_appointment_app.security.security_service;

import com.ait_31_2.doctor_appointment_app.domain.LoginForm;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.security.security_dto.AuthInfo;
import com.ait_31_2.doctor_appointment_app.security.security_dto.TokenResponseDto;
import com.ait_31_2.doctor_appointment_app.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing user authentication.
 * Handles user login, token generation, and authentication information retrieval.
 */
@Service
public class AuthService {
    private UserService userService;
    private TokenService tokenService;
    private Map<String, String> refreshStorage;
    private BCryptPasswordEncoder encoder;

    /**
     * Constructs an instance of AuthService with the specified dependencies.
     *
     * @param userService  The {@link UserService} instance for managing user data.
     * @param tokenService The {@link TokenService} instance for token generation and validation.
     * @param encoder      The BCryptPasswordEncoder instance for password encoding and validation.
     */
    public AuthService(UserService userService, TokenService tokenService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.refreshStorage = new HashMap<>();
    }

    /**
     * Logs in a user based on the provided login credentials.
     * Generates and returns an access token and refresh token upon successful login.
     *
     * @param inboundUser The {@link LoginForm} object containing user login credentials.
     * @return A TokenResponseDto containing the generated access token and refresh token.
     * @throws AuthException If the provided password is incorrect.
     */
    public TokenResponseDto login(@Nonnull LoginForm inboundUser) throws AuthException {
        String username = inboundUser.getUsername();
        User foundUser = (User) userService.loadUserByUsername(username);

        if (encoder.matches(inboundUser.getPassword(), foundUser.getPassword())) {
            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);
            refreshStorage.put(username, refreshToken);
            return new TokenResponseDto(accessToken, refreshToken, "OK");
        } else {
            throw new AuthException("Password is incorrect");
        }
    }

    /**
     * Retrieves a new access token based on the provided refresh token.
     * Validates the refresh token and generates a new access token if the refresh token is valid.
     *
     * @param refreshToken The refresh token used to generate a new access token.
     * @return A {@link TokenResponseDto} containing the new access token.
     */
    public TokenResponseDto getAccessToken(@Nonnull String refreshToken) {
        if (tokenService.validateRefreshToken(refreshToken)) {
            Claims refreshClaims = tokenService.getRefreshClaims(refreshToken);
            String username = refreshClaims.getSubject();
            String savedRefreshToken = refreshStorage.get(username);

            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                User user = (User) userService.loadUserByUsername(username);
                String accessToken = tokenService.generateAccessToken(user);
                return new TokenResponseDto(accessToken, null);
            }
        }
        return new TokenResponseDto(null, null);
    }

    /**
     * Retrieves authentication information for the current user.
     *
     * @return The {@link AuthInfo} object containing authentication details.
     */
    public AuthInfo getAuthInfo() {
        return (AuthInfo) SecurityContextHolder.getContext().getAuthentication();
    }
}
