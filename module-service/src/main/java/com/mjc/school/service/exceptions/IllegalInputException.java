package com.mjc.school.service.exceptions;

public class IllegalInputException extends RuntimeException{
    public IllegalInputException() {
        super("Illegal input for this model");
    }
}
