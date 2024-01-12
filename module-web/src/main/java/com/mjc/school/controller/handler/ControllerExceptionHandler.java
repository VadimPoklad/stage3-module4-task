package com.mjc.school.controller.handler;

import com.mjc.school.service.exceptions.IllegalInputException;
import com.mjc.school.service.exceptions.ModelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.mjc.school.controller.handler.Constants.*;


@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(Exception exception) {
        return new ResponseEntity<>(new ErrorMessage(CODE_MODEL_NOT_FOUND,
                exception.getMessage(),
                exception.getClass().getName()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalInputException.class)
    public ResponseEntity<ErrorMessage> illegalResourceInput(Exception exception) {
        return new ResponseEntity<>(new ErrorMessage(CODE_ILLEGAL_INPUT,
                exception.getMessage(),
                exception.getClass().getName()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentInput(Exception exception){
        return new ResponseEntity<>(new ErrorMessage(CODE_ILLEGAL_ARGUMENT,
                exception.getMessage(),
                exception.getClass().getName()), HttpStatus.BAD_REQUEST);
    }
}
