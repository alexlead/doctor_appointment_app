package com.ait_31_2.doctor_appointment_app.security.security_dto;

import com.ait_31_2.doctor_appointment_app.domain.classes.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class AuthInfo implements Authentication {

    private boolean authenticated;
    private String username;
    private String name;
    private Set<Role> roles;


    public AuthInfo(String username, Set<Role> roles) {
        this.username = username;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthInfo authInfo)) return false;
        return isAuthenticated() == authInfo.isAuthenticated() && Objects.equals(username, authInfo.username) && Objects.equals(getName(), authInfo.getName()) && Objects.equals(roles, authInfo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAuthenticated(), username, getName(), roles);
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "authenticated=" + authenticated +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", role=" + roles +
                '}';
    }
}
