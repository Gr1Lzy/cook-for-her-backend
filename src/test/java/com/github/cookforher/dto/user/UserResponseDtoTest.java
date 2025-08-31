package com.github.cookforher.dto.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserResponseDtoTest {

  @Test
  void userResponseDto_shouldCreateWithAllFields() {
    // Given & When
    UserResponseDto responseDto = new UserResponseDto();
    responseDto.setEmail("email@example.com");
    responseDto.setUsername("username");

    // Then
    assertEquals("email@example.com", responseDto.getEmail());
    assertEquals("username", responseDto.getUsername());
  }
}
