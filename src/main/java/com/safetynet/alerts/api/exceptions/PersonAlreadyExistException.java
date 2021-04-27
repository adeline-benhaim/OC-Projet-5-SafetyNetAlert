package com.safetynet.alerts.api.exceptions;

public class PersonAlreadyExistException extends RuntimeException{

    public PersonAlreadyExistException(String s) {
        super(s);
    }
}
