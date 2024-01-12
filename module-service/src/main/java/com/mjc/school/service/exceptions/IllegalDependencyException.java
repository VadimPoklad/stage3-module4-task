package com.mjc.school.service.exceptions;

public class IllegalDependencyException extends RuntimeException{
    public IllegalDependencyException() {
        super("Illegal input arguments for this query");
    }
}
