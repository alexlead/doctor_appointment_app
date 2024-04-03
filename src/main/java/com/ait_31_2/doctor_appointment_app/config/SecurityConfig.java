package com.ait_31_2.doctor_appointment_app.config;

import com.ait_31_2.doctor_appointment_app.security.security_filter.TokenFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private TokenFilter filter;

    public SecurityConfig(TokenFilter filter) {
        this.filter = filter;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        x -> x

                                .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/{userid}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/users/registration").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/doctor/{id}").hasAnyRole("ADMIN", "PATIENT")
                                .requestMatchers(HttpMethod.GET, "/api/users/doctors/").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/patient/{partName}").hasAnyRole("ADMIN", "DOCTOR")
                                .requestMatchers(HttpMethod.GET, "/api/auth/logout").hasAnyRole("ADMIN", "PATIENT", "DOCTOR")
                                .requestMatchers(HttpMethod.POST, "/api/auth/login", "api/auth/refresh", "/api/auth/logout").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/profile").hasAnyRole(
                                        "ADMIN","PATIENT","DOCTOR")
                                .requestMatchers(HttpMethod.PUT, "/api/profile").hasAnyRole(
                                        "ADMIN","PATIENT","DOCTOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/profile").hasAnyRole(
                                        "ADMIN","PATIENT","DOCTOR")


                                .requestMatchers(HttpMethod.GET, "/api/slots/{data}/{id}").hasAnyRole("ADMIN", "PATIENT", "DOCTOR")
                                //.requestMatchers(HttpMethod.GET, "/api/slots/").hasAnyRole("ADMIN", "PATIENT", "DOCTOR")
                                .requestMatchers(HttpMethod.GET, "/api/appointments/patient/{timeStart}/{timeEnd}").hasAnyRole("PATIENT", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/appointments/patient/future/").hasAnyRole("PATIENT", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/appointments/patient/past/").hasAnyRole("PATIENT", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/appointments/").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/appointments/{id}").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")


                                .anyRequest().authenticated()// все, что не перечисленно выше, доступно аутентифицированным пользователям
                )
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Doctor Appointment System app")
                        .description("APIs for managing appointments at a family medicine center")
                        .version("1.0.0").contact(new Contact().name("Tetiana Ilienko")
                                .email(" ").url(" "))
                        .license(new License().name("@TahaIl")
                                .url("")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }


}


