package org.example.userservice.Security.Services;

import org.example.userservice.Models.User;
import org.example.userservice.Repositories.UserRepository;
import org.example.userservice.Security.Models.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements
        org.springframework.security.core.userdetails.UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByEmail(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException(
                    "User with email id "+username+"doesn't exist"
            );
        }
        UserDetails userDetails = new CustomUserDetails(optionalUser.get());
        return userDetails;
    }
}
