package com.leolm.imageliteapi.application.handlers;

import com.leolm.imageliteapi.domain.exceptions.UserAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler({ UserAlreadyExistsException.class })
    public ResponseEntity handleUserConflict(
            Exception ex, WebRequest request) {
        Map<String,String > erro  = Map.of("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity handleAccessDeniedException(
            Exception ex, WebRequest request) {
        Map<String,String > erro  = Map.of("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleValidationExceptions(ConstraintViolationException constraintViolations) {
        Set<String> errors = new HashSet<>();

        errors.addAll(constraintViolations.getConstraintViolations().stream()
                .map(constraintViolation -> String.format("%s -> '%s' %s", constraintViolation.getPropertyPath(),
                                constraintViolation.getInvalidValue(), constraintViolation.getMessage())).collect(Collectors.toSet()));


    return ResponseEntity.badRequest().body(errors);
    }
}

