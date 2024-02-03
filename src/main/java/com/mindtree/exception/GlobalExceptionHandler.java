package com.mindtree.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TYPE_ERROR = "BUSINESS_APP";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        List<String> details = new ArrayList<>();
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .type(TYPE_ERROR)
                .cause("SERVER_ERROR")
                .messages(ex.getMessage())
                .details(details)
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<String>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .type(TYPE_ERROR)
                .cause("UN_AUTHORIZED")
                .messages("Invalid Login Credentials")
                .details(details)
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleAppException(ResourceNotFoundException resourceNotFoundException, WebRequest request) {
        List<String> details = new ArrayList<>();
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .type(TYPE_ERROR)
                .cause("RESOURCE_NOT_FOUND_ERROR")
                .messages(resourceNotFoundException.getMessage())
                .details(details)
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


}