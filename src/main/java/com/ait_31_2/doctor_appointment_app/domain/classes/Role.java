package com.ait_31_2.doctor_appointment_app.domain.classes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

/**
 * Entity class representing a user role.
 * Contains information about a specific role, including ID and name.
 */
@Entity
@Table(name = "role")
@Schema(description = "Entity class representing a user role.")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "The ID of the role.")
    private int id;
    @Column(name = "rolename")
    @Schema(description = "The Name of the role.")
    private String name;

    public Role() {
    }

    /**
     * Constructs a new Role object with the specified parameters.
     *
     * @param id   the ID of the role
     * @param name the name of the role
     */
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role1)) return false;
        return id == role1.id && Objects.equals(name, role1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + name + '\'' +
                '}';
    }


}
