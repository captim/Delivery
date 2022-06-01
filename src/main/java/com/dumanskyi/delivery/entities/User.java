package com.dumanskyi.delivery.entities;

import com.dumanskyi.delivery.entities.validators.annotations.DatabaseField;
import com.dumanskyi.delivery.entities.validators.annotations.Unique;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @NotNull
    @Size(min=2, max=50)
    private String firstName;
    @NotNull
    @Size(min=2, max=50)
    private String lastName;
    private String patronymic;
    @NotNull
    @Size(min=5, max=30)
    @Unique(databaseField = DatabaseField.EMAIL)
    private String email;
    @NotNull
    @Size(min=8, max=30)
    private String password;
    @NotNull
    @Size(min=4, max=30)
    @Unique(databaseField = DatabaseField.USERNAME)
    private String username;

    @Column(name = "role_id")
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singleton(authority);
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
