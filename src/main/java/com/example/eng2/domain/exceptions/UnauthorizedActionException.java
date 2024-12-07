package com.example.eng2.domain.exceptions;

public class UnauthorizedActionException extends RuntimeException{

    public UnauthorizedActionException(String message){
        super(message);
    }
}
