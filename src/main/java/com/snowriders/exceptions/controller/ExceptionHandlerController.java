package com.snowriders.exceptions.controller;

import com.snowriders.exceptions.DuplicateEntryException;
import com.snowriders.exceptions.EmailException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {


    //todo return content type json
    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getMessage();

//        return new ResponseEntity<>(bodyOfResponse, NOT_FOUND);

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(value = EmailException.class)
    protected ResponseEntity<Object> handleEmailException(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUsernameNotFoundException(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(value = DuplicateEntryException.class)
    protected ResponseEntity<Object> handleDuplicateEntryException(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), BAD_REQUEST, request);
    }
}
