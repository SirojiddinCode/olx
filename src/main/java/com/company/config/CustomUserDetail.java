package com.company.config;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetail implements UserDetails {

    private ProfileEntity profile;
    public CustomUserDetail(ProfileEntity profile){
        this.profile=profile;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(profile.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return profile.getPassword();
    }

    @Override
    public String getUsername() {
        return profile.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return profile.getStatus().equals(ProfileStatus.ACTIVE);
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

    public ProfileEntity getProfile() {
        return profile;
    }
}
