package com.ait_31_2.doctor_appointment_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        x -> x
                                .requestMatchers(HttpMethod.POST, "/user/registration").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/authorisation/{username}/{password}").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/profile/{userid}"authorisation).hasAnyRole(
//                                        "ADMIN","PATIENT","DOCTOR")
//                                .requestMatchers(HttpMethod.PUT, "/profile/{userid}").hasAnyRole(
//                                        "ADMIN","PATIENT","DOCTOR")
//                                .requestMatchers(HttpMethod.DELETE, "/profile/{userid}").hasAnyRole(
//                                        "ADMIN","PATIENT","DOCTOR")
                                .requestMatchers(HttpMethod.GET, "/user/all").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/user/by_id/{userid}").hasAnyRole(
                                        "ADMIN", "PATIENT", "DOCTOR")


                                .anyRequest().authenticated()// все, что не перечисленно выше, доступно аутентифицированным пользователям
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
