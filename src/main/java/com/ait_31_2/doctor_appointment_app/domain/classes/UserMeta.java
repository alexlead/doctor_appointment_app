package com.ait_31_2.doctor_appointment_app.domain.classes;

import com.ait_31_2.doctor_appointment_app.domain.interfaces.UserMetaInterface;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

/**
 * Entity class representing metadata associated with a user.
 * Contains information about a specific metadata key-value pair.
 *
 * @author Alexander
 * @version 1.1.0
 */
@Entity
@Table(name = "user_meta")
public class UserMeta implements UserMetaInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userId;
    @Column(name = "meta_key")
    @NotEmpty
    @Size(min = 3)
    private String metaKey;
    @Column(name = "meta_value")
    @NotEmpty
    @Size(min = 3)
    private String metaValue;

    public UserMeta() {
    }

    public UserMeta(int id, User userId, String metaKey, String metaValue) {
        this.id = id;
        this.userId = userId;
        this.metaKey = metaKey;
        this.metaValue = metaValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMeta userMeta = (UserMeta) o;
        return id == userMeta.id && Objects.equals(userId, userMeta.userId) && Objects.equals(metaKey, userMeta.metaKey) && Objects.equals(metaValue, userMeta.metaValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, metaKey, metaValue);
    }

    @Override
    public String toString() {
        return "UserMeta{" +
                "id=" + id +
                ", userId=" + userId +
                ", metaKey='" + metaKey + '\'' +
                ", metaValue='" + metaValue + '\'' +
                '}';
    }

}
