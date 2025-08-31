package com.github.cookforher.util;

import com.github.cookforher.entity.Role;
import com.github.cookforher.entity.User;
import com.github.cookforher.util.jwt.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenUtilTest {

  private JwtTokenUtil jwtTokenUtil;
  private User testUser;

  @BeforeEach
  void setUp() {
    jwtTokenUtil = new JwtTokenUtil();
    ReflectionTestUtils.setField(jwtTokenUtil, "tokenSecret", "dGVzdC1zZWNyZXQtdGVzdC1zZWNyZXQtdGVzdC1zZWNyZXQtdGVzdC1zZWNyZXQ=");
    ReflectionTestUtils.setField(jwtTokenUtil, "accessTokenExpiration", Duration.ofMinutes(15));
    ReflectionTestUtils.setField(jwtTokenUtil, "refreshTokenExpiration", Duration.ofDays(7));

    testUser = new User();
    ReflectionTestUtils.setField(testUser, "id", 1L);
    testUser.setUsername("testuser");
    testUser.setRole(Role.ROLE_USER);
  }

  @Test
  void generateAccessToken_shouldReturnValidToken() {
    // When
    String token = jwtTokenUtil.generateAccessToken(testUser);

    // Then
    assertThat(token).isNotNull();
    assertThat(jwtTokenUtil.isAccessTokenValid(token)).isTrue();
    assertThat(jwtTokenUtil.extractUserId(token)).isEqualTo(1L);
    assertThat(jwtTokenUtil.extractUserRole(token)).isEqualTo("ROLE_USER");
  }

  @Test
  void generateRefreshToken_shouldReturnValidToken() {
    // When
    String token = jwtTokenUtil.generateRefreshToken(testUser);

    // Then
    assertThat(token).isNotNull();
    assertThat(jwtTokenUtil.isRefreshTokenValid(token)).isTrue();
    assertThat(jwtTokenUtil.extractUserId(token)).isEqualTo(1L);
  }

  @Test
  void isAccessTokenValid_shouldReturnFalse_forRefreshToken() {
    // Given
    String refreshToken = jwtTokenUtil.generateRefreshToken(testUser);

    // When & Then
    assertThat(jwtTokenUtil.isAccessTokenValid(refreshToken)).isFalse();
  }

  @Test
  void isRefreshTokenValid_shouldReturnFalse_forAccessToken() {
    // Given
    String accessToken = jwtTokenUtil.generateAccessToken(testUser);

    // When & Then
    assertThat(jwtTokenUtil.isRefreshTokenValid(accessToken)).isFalse();
  }
}

