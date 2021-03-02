package com.udacity.jdnd.course3.critter.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage, null);
    }
}
