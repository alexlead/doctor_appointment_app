package com.ait_31_2.doctor_appointment_app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title ="Doctor Appointment System app",
                description = "APIs for managing appointments at a family medicine center.",
                version = "1.0.0",
                contact = @Contact (
                        name = "AIT, Team 20",
                        email = "tatyanailyenko@gmai.com"

                )
        )
)
public class SwaggerConfig {
}
