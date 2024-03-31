package com.project.salesmanagementsystem.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Error> objectNotFoundExceptionHandler(ObjectNotFoundException e){
        Error error = new Error(e.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Error> invalidInputExceptionHandler(InvalidInputException e){
        Error error = new Error(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
