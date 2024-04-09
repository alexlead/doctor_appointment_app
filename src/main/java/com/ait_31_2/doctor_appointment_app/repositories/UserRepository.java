package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for managing users.
 * Handles database operations related to users.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return the {@link User} object if found, null otherwise
     */
    User findByUsername(String username);

    /**
     * Finds all users with a specific role.
     *
     * @param rolename the name of the role to search for
     * @return a list of users with the specified role
     */
    @Query(value = "SELECT u.* FROM user u JOIN user_role ur ON u.id = ur.user_id JOIN role r ON ur.role_id = r.id WHERE (r.rolename = :rolename);", nativeQuery = true)
    List<User> findAllByRole(@Param("rolename") String rolename);

    /**
     * Finds a user by name, surname, and role.
     *
     * @param name     the name of the user
     * @param surname  the surname of the user
     * @param rolename the name of the role to search for
     * @return the {@link User} object if found, null otherwise
     */
    @Query(value = "SELECT u.* FROM user u JOIN role r ON u.role_id = r.id WHERE (u.name = :name) AND (u.surname = :surname)  AND (r.rolename = :rolename);", nativeQuery = true)
    User findUserByNameAndRole(@Param("name") String name, @Param("surname") String surname, @Param("rolename") String rolename);

    /**
     * Finds users by a part of their surname.
     *
     * @param partName the part of the surname to search for
     * @return a list of users whose surname contains the specified part
     */
    @Query(value = "SELECT * FROM user WHERE (surname LIKE %:partName%)  LIMIT 5 ;", nativeQuery = true)
    List<User> findUserByPartName(@Param("partName") String partName);

}
