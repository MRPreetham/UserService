package org.example.userservice.Controllers;

import org.example.userservice.Dtos.*;
import org.example.userservice.Exceptions.InvalidCredentialException;
import org.example.userservice.Exceptions.InvalidTokenException;
import org.example.userservice.Exceptions.UserExistException;
import org.example.userservice.Exceptions.UserNotFoundException;
import org.example.userservice.Models.User;
import org.example.userservice.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final LoginResponseDto responseDto;
    private final LogOutResponseDto logOutResponseDto;
    private final SignUpResponseDto signUpResponseDto;
    @Autowired
    public UserController(UserService userService, LoginResponseDto responseDto,
                          LogOutResponseDto logOutResponseDto, SignUpResponseDto signUpResponseDto){
        this.userService = userService;
        this.responseDto = responseDto;
        this.logOutResponseDto = logOutResponseDto;
        this.signUpResponseDto = signUpResponseDto;
    }
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto)
            throws UserNotFoundException, InvalidCredentialException {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        responseDto.setMessage(userService.login(email,password));
        return responseDto;
    }
    @DeleteMapping("/logout/{token}")
    public LogOutResponseDto logOut(@PathVariable("token") String token) throws InvalidTokenException {
        logOutResponseDto.setMessage(userService.logout(token));
        return logOutResponseDto;
    }
    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto)
            throws UserExistException {
        String username = signUpRequestDto.getUsername();
        String password = signUpRequestDto.getPassword();
        String email = signUpRequestDto.getEmail();
        User user = userService.signUp(email,password,username);
        signUpResponseDto.setEmail(user.getEmail());
        signUpResponseDto.setUsername(user.getUsername());
        signUpResponseDto.setRoles(user.getRoles());
        return signUpResponseDto;
    }
}
