package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing roles.
 * <p>
 * Provides methods to interact with the database for role-related operations.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to find
     * @return the {@link Role} entity corresponding to the given name
     */
    Role findByName(String name);
}
