package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query(value = "SELECT * FROM doctor_appointment_system.user where (role_id = :role);", nativeQuery = true)
    List<User> findAllByRole(@Param("role") int role);
    @Query(value = "SELECT * FROM doctor_appointment_system.user where (surname = :surname and role_id = :role);", nativeQuery = true)
    User findUserByNameAndRole(@Param ("surname") String surname, @Param("role") int role);

}
