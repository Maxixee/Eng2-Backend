package com.example.eng2.domain.exceptions;

public class InvalidRegistrationInformationException extends RuntimeException{

    public InvalidRegistrationInformationException(String message){
        super(message);
    }
}
