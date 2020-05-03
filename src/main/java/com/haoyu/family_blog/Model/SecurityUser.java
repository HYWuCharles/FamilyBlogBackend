package com.haoyu.family_blog.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/21.
 */
public class SecurityUser extends User implements UserDetails {

    public SecurityUser(User user) {
        if (user != null) {
            this.setUsername(user.getUsername());
            this.setAvatar(user.getAvatar());
            this.setCreateTime(user.getCreateTime());
            this.setId(user.getId());
            this.setLoginTime(user.getLoginTime());
            this.setEmail(user.getEmail());
            this.setNickName(user.getNickName());
            this.setNote(user.getNote());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
            this.setRole(user.getRole());
        }
    }

    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.getStatus() == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getStatus() == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.getStatus() == 1;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus() == 1;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
