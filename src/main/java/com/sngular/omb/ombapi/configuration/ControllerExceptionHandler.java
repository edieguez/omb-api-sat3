package com.sngular.omb.ombapi.configuration;

import com.sngular.omb.ombapi.model.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = "Missing required parameter[s]: ";
        message += ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<ResponseWrapper> handleMethodArgumentNotValid(IllegalArgumentException ex) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .error(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }
}
