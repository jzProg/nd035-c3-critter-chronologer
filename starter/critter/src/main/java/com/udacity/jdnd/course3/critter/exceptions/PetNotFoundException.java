package com.udacity.jdnd.course3.critter.exceptions;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String errorMessage) {
        super(errorMessage, null);
    }
}
