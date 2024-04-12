package org.example.userservice.Security.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.userservice.Models.Role;
@JsonDeserialize
public class CustomGrantedAuthority implements org.springframework.security.core.GrantedAuthority {
    //create attributes with same name as get_ method name
    private String authority;
    public CustomGrantedAuthority(){}
    public CustomGrantedAuthority(Role role){
        this.authority = role.getTitle();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
