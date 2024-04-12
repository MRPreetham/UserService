package org.example.userservice.Services;

import org.example.userservice.Exceptions.InvalidCredentialException;
import org.example.userservice.Exceptions.InvalidTokenException;
import org.example.userservice.Exceptions.UserExistException;
import org.example.userservice.Exceptions.UserNotFoundException;
import org.example.userservice.Models.User;

public interface UserService {
    String login(String email, String password) throws UserNotFoundException, InvalidCredentialException;

    String logout(String token) throws InvalidTokenException;
    User signUp(String email,String password,String username) throws UserExistException;

}
