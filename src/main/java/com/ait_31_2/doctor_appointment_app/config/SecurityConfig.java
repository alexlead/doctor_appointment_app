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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", c -> c.isAnnotationPresent(RequestMapping.class));
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        x -> x
                                .requestMatchers("/").permitAll()


                                .requestMatchers("/v3/**", "/swagger-ui/**", "swagger-resources","swagger-resources/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/registration").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/authorisation").permitAll()
                                .requestMatchers(HttpMethod.GET, "/user/doctors").permitAll()
                                .requestMatchers(HttpMethod.GET, "/user/doctor/{name}/{surname}")
                                .hasAnyRole("ADMIN", "PATIENT")
                                .requestMatchers(HttpMethod.GET, "user/patient/{partName}")
                                .hasAnyRole("ADMIN", "DOCTOR")
//                                .requestMatchers(HttpMethod.GET, "/profile/{userid}"authorisation).hasAnyRole(
//                                        "ADMIN","PATIENT","DOCTOR")
//                                .requestMatchers(HttpMethod.PUT, "/profile/{userid}").hasAnyRole(
//                                        "ADMIN","PATIENT","DOCTOR")
//                                .requestMatchers(HttpMethod.DELETE, "/profile/{userid}").hasAnyRole(
//                                        "ADMIN","PATIENT","DOCTOR")
                                .requestMatchers(HttpMethod.GET, "/user/all").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/user/{userid}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/slot/free").hasAnyRole("ADMIN", "PATIENT", "DOCTOR")
                                .requestMatchers(HttpMethod.GET, "/slot/all").hasAnyRole("ADMIN", "PATIENT", "DOCTOR")
                                .requestMatchers(HttpMethod.GET, "/appointment/patient/{patientId}/{timeStart}/{timeEnd}").hasRole("PATIENT")
                                .requestMatchers(HttpMethod.GET, "patient/future/{patientId}").hasRole("PATIENT")
                                .requestMatchers(HttpMethod.GET, "patient/past/{patientId}").hasRole("PATIENT")


                                .anyRequest().authenticated()// все, что не перечисленно выше, доступно аутентифицированным пользователям
                )

                .httpBasic(Customizer.withDefaults())
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );
        return http.build();
    }


}


