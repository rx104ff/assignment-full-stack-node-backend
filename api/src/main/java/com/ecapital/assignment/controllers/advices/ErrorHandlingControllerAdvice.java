package com.ecapital.assignment.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolationException;


/**
 * ErrorHandlingControllerAdvice is defined to handle controller violations
 * It is injected automatically when an endpoint is called with invalid request body
 * Violations are returned as a list for the frontend to deal with
 * */
@ControllerAdvice
class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<ValidationErrorResponse> onConstraintValidationException(ConstraintViolationException e) {
        var error = new ValidationErrorResponse();
        for (var violation : e.getConstraintViolations()) {
            error.getViolations().add(new ValidationViolation(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<ValidationErrorResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var error = new ValidationErrorResponse();
        for (var fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(new ValidationViolation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
