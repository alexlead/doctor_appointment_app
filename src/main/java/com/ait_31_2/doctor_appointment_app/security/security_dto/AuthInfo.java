package com.ait_31_2.doctor_appointment_app.security.security_dto;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Represents authentication information for a user.
 */
public class AuthInfo implements Authentication {

    private boolean authenticated;
    private String username;
    private String name;
    private String surname;

    private Set<Role> roles;

    /**
     * Constructs a new AuthInfo object with the specified authentication details.
     *
     * @param username The username of the authenticated user.
     * @param name     The first name of the authenticated user.
     * @param surname  The surname of the authenticated user.
     * @param roles    The roles assigned to the authenticated user.
     */
    public AuthInfo(String username, String name, String surname, Set<Role> roles) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthInfo authInfo)) return false;
        return isAuthenticated() == authInfo.isAuthenticated() && Objects.equals(username, authInfo.username) && Objects.equals(getName(), authInfo.getName()) && Objects.equals(getSurname(), authInfo.getSurname()) && Objects.equals(getRoles(), authInfo.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAuthenticated(), username, getName(), getSurname(), getRoles());
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "authenticated=" + authenticated +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", roles=" + roles +
                '}';
    }
}
