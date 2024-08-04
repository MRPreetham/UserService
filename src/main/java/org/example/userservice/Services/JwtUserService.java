package org.example.userservice.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.userservice.Dtos.SendEmailEventDto;
import org.example.userservice.Exceptions.InvalidCredentialException;
import org.example.userservice.Exceptions.InvalidTokenException;
import org.example.userservice.Exceptions.UserExistException;
import org.example.userservice.Exceptions.UserNotFoundException;
import org.example.userservice.Models.User;
import org.example.userservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

@Service
@Primary
public class JwtUserService implements UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private KafkaTemplate<String,String> kafkaTemplate;
    private SendEmailEventDto emailEventDto;
    private ObjectMapper objectMapper;
    @Autowired
    public JwtUserService(UserRepository userRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          KafkaTemplate<String,String> kafkaTemplate,
                          SendEmailEventDto emailEventDto,
                          ObjectMapper objectMapper){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.kafkaTemplate = kafkaTemplate;
        this.emailEventDto = emailEventDto;
        this.objectMapper = objectMapper;
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
        User savedUser = userRepository.save(user);
        emailEventDto.setTo("ppreetham156@gmail.com");
        emailEventDto.setFrom("mrpreetham8@gmail.com");
        emailEventDto.setSubject("Welcome to EmailService");
        emailEventDto.setBody("Hello Preetham");
        try {
            kafkaTemplate.send("sendEmail",
                    objectMapper.writeValueAsString(emailEventDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return savedUser;
    }
}
