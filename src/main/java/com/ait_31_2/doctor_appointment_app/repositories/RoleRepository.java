package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
