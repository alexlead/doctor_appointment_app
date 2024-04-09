package com.ait_31_2.doctor_appointment_app.repositories;

import com.ait_31_2.doctor_appointment_app.domain.classes.UserMeta;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * Repository interface for managing user metadata.
 * <p>
 * Provides methods to interact with the database for user metadata-related operations.
 *
 * @author Alexandr
 * @version 1.0.0
 */
public interface UserMetaRepository extends JpaRepository<UserMeta, Integer> {

    /**
     * Finds all user metadata by user ID.
     *
     * @param userId the ID of the user for whom to retrieve metadata
     * @return a collection of UserMeta objects representing the metadata for the specified user
     */
    @Query(value = "SELECT * FROM user_meta WHERE user_id = :userId", nativeQuery = true)
    Collection<UserMeta> findAllByUserId(@Param("userId") int userId);

    /**
     * Finds user metadata by user ID and metadata key.
     *
     * @param userId  the ID of the user
     * @param metaKey the metadata key
     * @return a {@link UserMeta} object representing the metadata for the specified user and key
     */
    @Query(value = "SELECT * FROM user_meta WHERE (user_id = :userId) AND meta_key LIKE (:meta_key) LIMIT 1", nativeQuery = true)
    UserMeta findByUserIdAndMetaKey(@Param("userId") int userId, @Param("meta_key") String metaKey);

    /**
     * Deletes user metadata by user ID and metadata key.
     *
     * @param userId  the ID of the user
     * @param metaKey the metadata key
     * @return the number of metadata records deleted
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_meta WHERE user_id = :userId AND meta_key LIKE (:meta_key)", nativeQuery = true)
    int deleteByUserIdAndMetaKey(@Param("userId") int userId, @Param("meta_key") String metaKey);

    /**
     * Retrieves list of all doctors with  photos.
     *
     * @return a collection of Tuple objects representing doctors with photo metadata
     */
    @Query(value = "SELECT u.id as id, u.name as name, u.surname as surname, um.meta_value as photo FROM user_meta as um " +
            "LEFT JOIN user as u ON u.id = um.user_id " +
            "LEFT JOIN user_role as ur ON ur.user_id = u.id " +
            "LEFT JOIN role as r ON r.id = ur.role_id " +
            "WHERE um.meta_key LIKE (\"Photo\") " +
            "AND r.rolename LIKE (\"%DOCTOR%\")", nativeQuery = true)
    Collection<Tuple> getAllDoctorsWithPhoto();

}