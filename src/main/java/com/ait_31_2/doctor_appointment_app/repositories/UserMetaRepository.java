package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.UserMeta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserMetaRepository extends JpaRepository<UserMeta, Integer> {

    @Query(value = "SELECT * FROM user_meta WHERE user_id = :userId", nativeQuery = true)
    Collection<UserMeta> findAllByUserId(@Param("userId") int userId);

    @Query(value = "SELECT * FROM user_meta WHERE (user_id = :userId) AND meta_key LIKE (:meta_key) LIMIT 1", nativeQuery = true)
    UserMeta findByUserIdAndMetaKey(@Param("userId") int userId, @Param("meta_key") String metaKey);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_meta WHERE user_id = :userId AND meta_key LIKE (:meta_key)", nativeQuery = true)
    int deleteByUserIdAndMetaKey(@Param("userId") int userId, @Param("meta_key") String metaKey);

}