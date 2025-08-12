package com.github.cookforher.exception.handler;

import com.github.cookforher.exception.custom.AuthenticationException;
import com.github.cookforher.exception.custom.EntityExistsException;
import com.github.cookforher.exception.custom.EntityNotFoundException;
import com.github.cookforher.exception.custom.InvalidTokenException;
import com.github.cookforher.util.ErrorResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex) {
    Map<String, String> errorResponse = ErrorResponseBuilder.build(
        ex.getMessage(),
        HttpStatus.UNAUTHORIZED);

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<Map<String, String>> handleEntityExistsException(EntityExistsException ex) {
    Map<String, String> errorResponse = ErrorResponseBuilder.build(
        ex.getMessage(),
        HttpStatus.CONFLICT);

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
    Map<String, String> errorResponse = ErrorResponseBuilder.build(
        ex.getMessage(),
        HttpStatus.NOT_FOUND);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<Map<String, String>> handleInvalidTokenException(InvalidTokenException ex) {
    Map<String, String> errorResponse = ErrorResponseBuilder.build(
        ex.getMessage(),
        HttpStatus.UNAUTHORIZED);

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }
}
