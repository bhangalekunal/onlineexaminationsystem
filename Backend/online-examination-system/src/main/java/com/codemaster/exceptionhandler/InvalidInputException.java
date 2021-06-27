package com.codemaster.exceptionhandler;


public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String exception) {
        super(exception);
    }

}