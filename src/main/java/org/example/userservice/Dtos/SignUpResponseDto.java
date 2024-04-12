package org.example.userservice.Dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userservice.Models.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class SignUpResponseDto {
    private String username;
    private String email;
    private List<Role> roles;
}
