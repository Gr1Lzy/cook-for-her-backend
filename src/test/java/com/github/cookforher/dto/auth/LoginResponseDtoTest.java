package com.github.cookforher.dto.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginResponseDtoTest {

  @Test
  void loginResponseDto_shouldBuildWithAllFields() {
    // Given & When
    LoginResponseDto dto = LoginResponseDto.builder()
        .accessToken("access123")
        .refreshToken("refresh456")
        .build();

    // Then
    assertEquals("access123", dto.getAccessToken());
    assertEquals("refresh456", dto.getRefreshToken());
  }
}
