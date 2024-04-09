package com.ait_31_2.doctor_appointment_app.security.security_filter;

import com.ait_31_2.doctor_appointment_app.security.security_dto.AuthInfo;
import com.ait_31_2.doctor_appointment_app.security.security_service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * Filter class responsible for intercepting incoming requests to extract and validate access tokens.
 * Extends GenericFilterBean for generic filter functionality.
 */
@Component
public class TokenFilter extends GenericFilterBean {
    private TokenService service;

    /**
     * Initializes the TokenFilter with the specified {@link TokenService}.
     *
     * @param service The TokenService used for token validation and authentication.
     */
    public TokenFilter(TokenService service) {
        this.service = service;
    }

    /**
     * Filters incoming requests to extract and validate access tokens.
     *
     * @param request  The incoming HttpServletRequest object.
     * @param response The outgoing HttpServletResponse object.
     * @param chain    The FilterChain object for executing the next filter in the chain.
     * @throws IOException      If an I/O error occurs during the filter chain processing.
     * @throws ServletException If any servlet-specific error occurs during the filter chain processing.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = getTokenFromRequest((HttpServletRequest) request);

        if (token != null && service.validateAccessToken(token)) {
            Claims claims = service.getAccessClaims(token);
            AuthInfo authInfo = service.generateAuthInfo(claims);
            authInfo.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authInfo);
        }

        chain.doFilter(request, response);
    }

    /**
     * Retrieves the access token from the request headers or cookies.
     *
     * @param request The incoming HttpServletRequest object.
     * @return The access token extracted from the request, or null if not found.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Access-Token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }


        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

}
