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


/**
 * GlobalExceptionHandler is custom handler class to handle all exceptions throw in this application.
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Logger to log messages to console and log file.
     */
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handler to parse json filed validation exception
     *
     * @param  ex       Constraint violation exception on filed in requested json
     * @Param  request  received request
     * @return          Customised response after filtering un-necessary content.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex,
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

    /**
     * Handler to parse transaction date parse exception.
     *
     * @param  ex       transaction date parse exception
     * @Param  request  received request
     * @return          Customised response after filtering un-necessary content.
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    protected ResponseEntity<Object> httpMessageConversionException(HttpMessageConversionException ex,
                                                                  WebRequest request) {
        logger.error("Invalid date format sent in request {}  {} error {}",
                ((ServletWebRequest) request).getRequest().getMethod(),
                ((ServletWebRequest) request).getRequest().getRequestURI(), ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
