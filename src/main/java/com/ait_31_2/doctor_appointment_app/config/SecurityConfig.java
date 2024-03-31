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
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

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
                                .requestMatchers(HttpMethod.GET, "api/users/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/auth/logout").hasAnyRole("ADMIN", "PATIENT", "DOCTOR")
                                .requestMatchers(HttpMethod.GET, "/api/users/doctors").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/users/registration").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/login", "api/auth/refresh","/api/auth/logout").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/doctor/{name}/{surname}")
                                .hasAnyRole("ADMIN", "PATIENT")
                                .requestMatchers(HttpMethod.GET, "/api/users/patient/{partName}")
                                .hasAnyRole("ADMIN", "DOCTOR")
//                                .requestMatchers(HttpMethod.GET, "/profile/{userid}"authorisation).hasAnyRole(
//                                        "ADMIN","PATIENT","DOCTOR")
//                                .requestMatchers(HttpMethod.PUT, "/profile/{userid}").hasAnyRole(
//                                        "ADMIN","PATIENT","DOCTOR")
//                                .requestMatchers(HttpMethod.DELETE, "/profile/{userid}").hasAnyRole(
//                                        "ADMIN","PATIENT","DOCTOR")


                                .requestMatchers(HttpMethod.POST, "/slot/free").hasAnyRole("ADMIN", "PATIENT", "DOCTOR")
                                .requestMatchers(HttpMethod.GET, "/slot/all").hasAnyRole("ADMIN", "PATIENT", "DOCTOR")
                                .requestMatchers(HttpMethod.GET, "/appointment/patient/{patientId}/{timeStart}/{timeEnd}").hasRole("PATIENT")
                                .requestMatchers(HttpMethod.GET, "patient/future/{patientId}").hasRole("PATIENT")
                                .requestMatchers(HttpMethod.GET, "patient/past/{patientId}").hasRole("PATIENT")


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
                                .email( " ").url(" "))
                        .license(new License().name("@TahaIl")
                                .url("")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }


}


