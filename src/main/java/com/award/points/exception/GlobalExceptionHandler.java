package com.award.points.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.context.request.ServletWebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(ConstraintViolationException ex,
                                                                  WebRequest request) {
        logger.error("Customer send invalid JSON body for request {}  {} error {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().stream().iterator().forEachRemaining(error -> {
            String fieldName = error.getPropertyPath().toString();
            String message = error.getMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(HttpMessageConversionException ex,
                                                                  WebRequest request) {
        logger.error("Invalid date format sent in request {}  {} error {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
