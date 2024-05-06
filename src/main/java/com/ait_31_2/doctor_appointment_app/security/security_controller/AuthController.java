package com.ait_31_2.doctor_appointment_app.security.security_controller;

import com.ait_31_2.doctor_appointment_app.domain.LoginForm;
import com.ait_31_2.doctor_appointment_app.security.security_dto.TokenRefreshRequest;
import com.ait_31_2.doctor_appointment_app.security.security_dto.TokenResponseDto;
import com.ait_31_2.doctor_appointment_app.security.security_service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing authentication-related endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication  controller",
        description = "")
public class AuthController {
    private AuthService service;
    /**
     * Initializes the AuthController with the specified {@link AuthService}.
     *
     * @param service The AuthService to be used for authentication operations.
     */
    public AuthController(AuthService service) {
        this.service = service;
    }

    /**
     * Endpoint for user authentication.
     * Allows users to log in to the Doctor Appointment System.
     *
     * @param loginForm The login credentials provided by the user.
     * @param response  The HttpServletResponse object for managing cookies.
     * @return ResponseEntity containing the access token if authentication is successful,
     *         otherwise returns a bad request with an error message.
     */
    @PostMapping("/login")
    @Operation(
            summary = "Authentication",
            description = "Login in app 'Doctor appointment system'. Available to all users."
    )
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm, HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = new Cookie("Access-Token", null);
        cookie.setPath("/api");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        try {
            TokenResponseDto tokenDto = service.login(loginForm, request);

            Cookie accessTokenCookie = new Cookie("Access-Token", tokenDto.getAccessToken());
            accessTokenCookie.setPath("/");
            accessTokenCookie.setHttpOnly(true);
            response.addCookie(accessTokenCookie);

            //  HTTP-only cookie
            Cookie refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            response.addCookie(refreshTokenCookie);

            return ResponseEntity.ok(tokenDto.getMessage());
        } catch (AuthException e) {
            TokenResponseDto tokenDto = new TokenResponseDto(e.getMessage());
            return new ResponseEntity<>(tokenDto.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> getNewAccessToken(@RequestBody TokenRefreshRequest request, HttpServletRequest httpRequest) {
        TokenResponseDto accessToken = service.getAccessToken(request.getRefreshToken(),httpRequest);
        return ResponseEntity.ok(accessToken);
    }


    /**
     * Endpoint for user logout.
     * Allows authenticated users to log out of the Doctor Appointment System.
     *
     * @param request  The HttpServletRequest object for accessing cookies.
     * @param response The HttpServletResponse object for managing cookies.
     * @return A redirect to the home page after successful logout.
     */
    @GetMapping("/logout")
    @Operation(
            summary = "Logout",
            description = "Logout  app 'Doctor appointment system'. Available to authenticated users."
    )
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Access-Token")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        return "redirect:/";
    }
}
