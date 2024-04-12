package org.example.userservice.Security.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.userservice.Models.Role;
import org.example.userservice.Models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@JsonDeserialize
public class CustomUserDetails implements UserDetails {
    //create attributes with same name as get_ method name
    private String password;
    private String username;
    private List<CustomGrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public CustomUserDetails(){
    }
    public CustomUserDetails(User user){
        this.password = user.getPassword();
        this.username = user.getEmail();
        List<Role> roles = user.getRoles();
        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role role:roles){
            CustomGrantedAuthority customGrantedAuthority = new CustomGrantedAuthority(role);
            grantedAuthorities.add(customGrantedAuthority);
        }
        this.authorities = grantedAuthorities;
        this.enabled = true;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.userId = user.getId();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
//        return user.getPassword();
        return password;
    }

    @Override
    public String getUsername() {
//        return user.getEmail();
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
