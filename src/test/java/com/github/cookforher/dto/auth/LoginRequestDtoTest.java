package com.github.cookforher.dto.auth;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginRequestDtoTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
  }

  @Test
  void loginRequestDto_shouldPassValidation_whenAllFieldsValid() {
    // Given
    LoginRequestDto dto = new LoginRequestDto();
    dto.setUsername("test");
    dto.setPassword("password");

    // When
    Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);

    // Then
    assertTrue(violations.isEmpty());
  }

  @Test
  void loginRequestDto_shouldFailValidation_whenUsernameBlank() {
    // Given
    LoginRequestDto dto = new LoginRequestDto();
    dto.setUsername("");
    dto.setPassword("password");

    // When
    Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);

    // Then
    assertEquals(1, violations.size());
    assertEquals("Username is required.", violations.iterator().next().getMessage());
  }

}
