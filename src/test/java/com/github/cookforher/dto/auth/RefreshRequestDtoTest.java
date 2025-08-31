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

class RefreshRequestDtoTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
  }

  @Test
  void refreshRequestDto_shouldPassValidation_whenRefreshNotBlank() {
    // Given
    RefreshRequestDto requestDto = new RefreshRequestDto();
    requestDto.setRefreshToken("refreshToken");

    // When
    Set<ConstraintViolation<RefreshRequestDto>> violations = validator.validate(requestDto);

    // Then
    assertTrue(violations.isEmpty());
  }

  @Test
  void refreshRequestDto_shouldFailValidation_whenBlank() {
    // Given
    RefreshRequestDto requestDto = new RefreshRequestDto();
    requestDto.setRefreshToken("");

    // When
    Set<ConstraintViolation<RefreshRequestDto>> violations = validator.validate(requestDto);

    // Then
    assertEquals(1, violations.size());
    assertEquals("Refresh Token is required.", violations.iterator().next().getMessage());
  }
}
