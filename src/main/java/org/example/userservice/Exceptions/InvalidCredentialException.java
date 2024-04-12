package org.example.userservice.Exceptions;

public class InvalidCredentialException extends Exception{
    public InvalidCredentialException(String message){
        super(message);
    }
}
