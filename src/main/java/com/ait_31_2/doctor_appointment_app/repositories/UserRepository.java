package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query(value = "SELECT u.* FROM doctor_appointment_system.user u JOIN role r ON u.role_id = r.id WHERE (r.rolename = :rolename);", nativeQuery = true)
    List<User> findAllByRole(@Param("rolename") String rolename);

    @Query(value = "SELECT u.* FROM doctor_appointment_system.user u JOIN role r ON u.role_id = r.id WHERE (u.name = :name) AND (u.surname = :surname)  AND (r.rolename = :rolename);", nativeQuery = true)
    User findUserByNameAndRole(@Param("name") String name, @Param("surname") String surname, @Param("rolename") String rolename);


    @Query(value = "SELECT * FROM doctor_appointment_system.user WHERE (surname LIKE %:partName%)  LIMIT 5 ;", nativeQuery = true)
    List<User> findUserByPartName(@Param("partName") String partName);

}
