package org.example.userservice.Services;

import org.example.userservice.Exceptions.InvalidCredentialException;
import org.example.userservice.Exceptions.InvalidTokenException;
import org.example.userservice.Exceptions.UserExistException;
import org.example.userservice.Exceptions.UserNotFoundException;
import org.example.userservice.Models.User;
import org.example.userservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class JwtUserService implements UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public JwtUserService(UserRepository userRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public String login(String email, String password)
            throws UserNotFoundException, InvalidCredentialException {
        return null;
    }

    @Override
    public String logout(String token)
            throws InvalidTokenException {
        return null;
    }

    @Override
    public User signUp(String email, String password, String username)
            throws UserExistException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if(!userOptional.isEmpty()){
            throw new UserExistException(
                    "user with email id "+email+" already exist"
            );
        }
        User user =  new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setUsername(username);
        return userRepository.save(user);
    }
}
