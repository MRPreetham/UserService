package org.example.userservice.ControllerAdvices;

import org.example.userservice.Dtos.ExceptionDto;
import org.example.userservice.Exceptions.InvalidCredentialException;
import org.example.userservice.Exceptions.InvalidTokenException;
import org.example.userservice.Exceptions.UserExistException;
import org.example.userservice.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    private final ExceptionDto exceptionDto;
    public ExceptionsHandler(ExceptionDto exceptionDto){
        this.exceptionDto = exceptionDto;
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> userNotFound(UserNotFoundException e){
        exceptionDto.setMessage(e.getMessage());
        ResponseEntity<ExceptionDto> response =  new ResponseEntity<>(
                exceptionDto,
                HttpStatus.NOT_FOUND
        );
        return response;
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ExceptionDto> InvalidCredentials(InvalidCredentialException e){
        exceptionDto.setMessage(e.getMessage());
        ResponseEntity<ExceptionDto> response =  new ResponseEntity<>(
                exceptionDto,
                HttpStatus.FORBIDDEN
        );
        return response;
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ExceptionDto> UserExistException(UserExistException e){
        exceptionDto.setMessage(e.getMessage());
        ResponseEntity<ExceptionDto> response =  new ResponseEntity<>(
                exceptionDto,
                HttpStatus.CONFLICT
        );
        return response;
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ExceptionDto> InvalidTokenException(InvalidTokenException e){
        exceptionDto.setMessage(e.getMessage());
        ResponseEntity<ExceptionDto> response =  new ResponseEntity<>(
                exceptionDto,
                HttpStatus.NOT_FOUND
        );
        return response;
    }
}
