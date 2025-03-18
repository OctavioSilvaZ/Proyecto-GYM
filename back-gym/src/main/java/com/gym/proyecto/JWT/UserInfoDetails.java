package com.gym.proyecto.JWT;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;

public class UserInfoDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(PersonalModel personalInfo, RolPersonalModel rol) {
        name = personalInfo.getNombre();
        password = personalInfo.getPassword();
        authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol.getRolId().getRol().toUpperCase()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
