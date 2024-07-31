package com.example.department.model;

import com.example.department.enums.UserRole;
import com.example.department.enums.UserStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(updatable = false)
    Long id;

    @Version
    @Column(updatable = true)
    Long version;

    @Getter
    @Column(name="email",unique = true,updatable = false)
    String email;

    @Column(name = "password")
    String password;

    @Getter
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    UserStatus status;

    @Getter
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    UserRole role;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.status = UserStatus.ACTIVE;
        this.role = UserRole.MANAGER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.equals(UserStatus.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return status.equals(UserStatus.ACTIVE);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
