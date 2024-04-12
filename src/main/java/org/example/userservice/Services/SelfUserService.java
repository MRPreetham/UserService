package org.example.userservice.Services;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.userservice.Exceptions.InvalidCredentialException;
import org.example.userservice.Exceptions.InvalidTokenException;
import org.example.userservice.Exceptions.UserExistException;
import org.example.userservice.Exceptions.UserNotFoundException;
import org.example.userservice.Models.Token;
import org.example.userservice.Models.User;
import org.example.userservice.Repositories.TokenRepository;
import org.example.userservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.Optional;

@Service
public class SelfUserService implements UserService{
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SelfUserService(UserRepository userRepository,
                           TokenRepository tokenRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String login(String email, String password)
            throws UserNotFoundException, InvalidCredentialException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    "User with email id " + email + " not found"
            );
        }
        if(!bCryptPasswordEncoder.matches(password,userOptional.get().getPassword())){
            throw new InvalidCredentialException(
                    "Invalid username or password"
            );
        }
        Token token = new Token();
        String value = RandomStringUtils.randomAlphanumeric(12);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime tomorrow = currentTime.plus(1, ChronoUnit.DAYS);
        Date date = Date.from(tomorrow.atZone(ZoneId.systemDefault()).toInstant());
        token.setUser(userOptional.get());
        token.setToken(value);
        token.setExpiryAt(date);
        tokenRepository.save(token);
        return value;
    }

    @Transactional
    public String logout(String token) throws InvalidTokenException {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if(tokenOptional.isEmpty()){
            throw new InvalidTokenException(
                    "Invalid token provided"
            );
        }
        Token savedToken = tokenOptional.get();
        if(savedToken.isDeleted() || !savedToken.getExpiryAt().after(new Date())){
            throw new InvalidTokenException(
                    "Invalid token provided"
            );
        }
        tokenRepository.deleteByToken(token);
        return "Logged out successFully";
    }

    @Override
    public User signUp(String email, String password, String username) throws UserExistException {
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
