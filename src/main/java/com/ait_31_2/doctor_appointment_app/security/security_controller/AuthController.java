package com.ait_31_2.doctor_appointment_app.security.security_controller;

import com.ait_31_2.doctor_appointment_app.domain.LoginForm;
import com.ait_31_2.doctor_appointment_app.security.security_dto.RefreshRequestDto;
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

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication  controller",
        description = "")
public class AuthController {
    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }


    @PostMapping("/login")
    @Operation(
            summary = "Authentication",
            description = "Login in app 'Doctor appointment system'. Available to all users."
    )
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        Cookie cookie = new Cookie("Access-Token", null);
        cookie.setPath("/api");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        try {
            TokenResponseDto tokenDto = service.login(loginForm);

            cookie = new Cookie("Access-Token", tokenDto.getAccessToken());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            return ResponseEntity.ok(tokenDto);
        } catch (AuthException e) {
            TokenResponseDto tokenDto = new TokenResponseDto(e.getMessage());
            return new ResponseEntity<>(tokenDto, HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/refresh")
//
//    public ResponseEntity<TokenResponseDto> getNewRefreshToken(@RequestBody RefreshRequestDto request) {
//        TokenResponseDto accessToken = service.getAccessToken(request.getRefreshToken());
//        return ResponseEntity.ok(accessToken);
//    }



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
