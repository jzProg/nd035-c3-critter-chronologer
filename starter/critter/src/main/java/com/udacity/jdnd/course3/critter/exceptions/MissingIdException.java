package com.udacity.jdnd.course3.critter.exceptions;

public class MissingIdException extends RuntimeException {
    public MissingIdException(String errorMessage) {
        super(errorMessage, null);
    }
}
