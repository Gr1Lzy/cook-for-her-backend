package com.github.cookforher.config;

import com.github.cookforher.util.jwt.JwtAuthFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

  @Mock
  private JwtAuthFilter jwtAuthFilter;

  @InjectMocks
  private SecurityConfig securityConfig;

  @Test
  void securityFilterChain_shouldReturnSecurityFilterChain() throws Exception {
    // Given
    HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);
    when(httpSecurity.build()).thenReturn(mock(DefaultSecurityFilterChain.class));

    // When
    SecurityFilterChain result = securityConfig.securityFilterChain(httpSecurity);

    // Then
    assertNotNull(result);
  }

  @Test
  void securityConfig_shouldBeCreatedWithJwtFilter() {
    // Given & When
    SecurityConfig config = new SecurityConfig(jwtAuthFilter);

    // Then
    assertNotNull(config);
  }
}
