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

class RegisterRequestDtoTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
  }

  @Test
  void registerRequestDto_shouldFailValidation_whenEmailNotCorrect() {
    // Given
    RegisterRequestDto requestDto = new RegisterRequestDto();
    requestDto.setUsername("username");
    requestDto.setEmail("invalid-email");
    requestDto.setPassword("password");

    // When
    Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(requestDto);

    // Then
    assertEquals(1, violations.size());
    assertEquals("Wrong email format.", violations.iterator().next().getMessage());
  }

  @Test
  void registerRequestDto_shouldFailValidation_whenPasswordSizeIncorrect() {
    // Given
    RegisterRequestDto requestDto = new RegisterRequestDto();
    requestDto.setUsername("username");
    requestDto.setEmail("email@example.com");
    requestDto.setPassword("size5");

    // When
    Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(requestDto);

    // Then
    assertEquals(1, violations.size());
    assertEquals("Password must be at least 8 characters.", violations.iterator().next().getMessage());
  }

  @Test
  void registerRequestDto_shouldPassValidation_whenAllFieldsCorrect() {
    // Given
    RegisterRequestDto requestDto = new RegisterRequestDto();
    requestDto.setUsername("username");
    requestDto.setEmail("email@example.com");
    requestDto.setPassword("password");

    // When
    Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(requestDto);

    // Then
    assertTrue(violations.isEmpty());
  }
}
