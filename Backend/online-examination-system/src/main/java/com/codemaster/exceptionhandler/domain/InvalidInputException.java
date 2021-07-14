package com.codemaster.exceptionhandler.domain;

public class InvalidInputException extends Exception{
    public InvalidInputException(String message) {
        super(message);
    }
}
