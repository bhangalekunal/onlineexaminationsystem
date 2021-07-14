package com.codemaster.exceptionhandler.domain;

public class NoActiveRoleFoundException extends Exception{
    public NoActiveRoleFoundException(String message) {
        super(message);
    }
}
