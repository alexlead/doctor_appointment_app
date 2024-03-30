package com.ait_31_2.doctor_appointment_app.security.security_controller;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import com.ait_31_2.doctor_appointment_app.security.security_dto.RefreshRequestDto;
import com.ait_31_2.doctor_appointment_app.security.security_dto.TokenResponseDto;
import com.ait_31_2.doctor_appointment_app.security.security_service.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody User user, HttpServletResponse response) {
        try {
            TokenResponseDto tokenDto = service.login(user);

            Cookie cookie = new Cookie("Access-Token", tokenDto.getAccessToken());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            return ResponseEntity.ok(tokenDto);
        } catch (AuthException e) {
            TokenResponseDto tokenDto = new TokenResponseDto(e.getMessage());
            return new ResponseEntity<>(tokenDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> getNewRefreshToken(@RequestBody RefreshRequestDto request) {
        TokenResponseDto accessToken = service.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(accessToken);
    }


    @GetMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Access-Token", null);
        cookie.setPath("/api");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
