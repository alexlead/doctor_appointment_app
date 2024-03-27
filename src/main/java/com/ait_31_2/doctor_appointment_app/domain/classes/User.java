package com.ait_31_2.doctor_appointment_app.domain.classes;

import com.ait_31_2.doctor_appointment_app.domain.interfaces.UserInterface;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "user")
@Schema(
        description = "Class user"
)
public class User implements UserInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(
            description = "Users`s id",
            example = "13",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private int id;

    @Column(name = "name")
    @NotNull
    @Pattern(
            regexp = "[A-ZА-Я][a-zа-я]{1,}",
            message = "The name must begin with a capital letter and contain at least 2 more lowercase letters and can only contain letters."
    )
    @Schema(
            description = "User`s name",
            example = "Ivan")
    private String name;

    @Column(name = "surname")
    @NotNull
    @Pattern(
            regexp = "[A-ZА-Я][a-zа-я]{1,}",
            message = "The surname must begin with a capital letter and contain at least 2 more lowercase letters and can only contain letters."
    )
    @Schema(
            description = "User`s surname",
            example = "Ivanov")
    private String surname;

    @Column(name = "email")
    @Schema(
            description = "email, which is a username",
            example = "iv_ivanjv@gm.com")
    @Email(message = "Incorrect email values are not allowed")
    private String username;

    @Column(name = "password")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "The password must contain at least one digit, one lowercase and one uppercase letter, " +
                    "one special character, and be at least 8 characters long."
    )
    @Schema(
            description = "Password",
            example = "123S!fghjk")
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @Schema(
            description = "Users`s role",
            example = "PATIENT",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Role role;

    public User() {
    }

    public User(int id, String name, String surname, String username, String password, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.getAuthority()));
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId() == user.getId() && Objects.equals(getName(), user.getName()) && Objects.equals(getSurname(), user.getSurname()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getRole(), user.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getUsername(), getPassword(), getRole());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public void clearRole() {
        this.role = null;
    }

}
