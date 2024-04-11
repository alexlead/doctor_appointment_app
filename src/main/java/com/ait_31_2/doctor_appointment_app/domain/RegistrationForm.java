package com.ait_31_2.doctor_appointment_app.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Registration Form for a new user.
 * Contains basic information for registration, including ID, name, surname, username and password.
 */
@Data
@Schema(
        description = "Registration form"
)
public class RegistrationForm {
//    @Pattern(
//            regexp = "[a-zа-я]{1,}",
//            message = "The name must begin with a capital letter and contain at least 2 more lowercase letters and can only contain letters."
//    )
    @NotEmpty
    @Schema(
            description = "User`s name",
            example = "Ivan")
    private String name;

//    @Pattern(
//            regexp = "[a-zа-я]{1,}",
//            message = "The surname must begin with a capital letter and contain at least 2 more lowercase letters and can only contain letters."
//    )
    @NotEmpty
    @Schema(
            description = "User`s surname",
            example = "Ivanov")
    private String surname;

    @Schema(
            description = "email, which is a username",
            example = "iv_ivanov@gm.com")
    @Email(message = "Incorrect email values are not allowed")
    private String username;
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "The password must contain at least one digit, one lowercase and one uppercase letter, " +
                    "one special character, and be at least 8 characters long."
    )
    @Schema(
            description = "Password",
            example = "123S!fghjk")
    private String password;

}
