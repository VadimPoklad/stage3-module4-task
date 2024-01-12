package com.mjc.school.service.exceptions;

public class ModelNotFoundException extends RuntimeException{

    public ModelNotFoundException() {
        super("Model not found");
    }
}
