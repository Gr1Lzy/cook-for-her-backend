package com.github.cookforher.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.Map;

@UtilityClass
public class ErrorResponseBuilder {

  private static final String MESSAGE = "message";
  private static final String STATUS = "status";

  public static Map<String, String> build(String message, HttpStatus status) {
    return Map.of(
        MESSAGE, message,
        STATUS, String.valueOf(status)
    );
  }
}
