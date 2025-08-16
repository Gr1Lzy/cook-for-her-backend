package com.github.cookforher.config;

import com.github.cookforher.entity.User;
import com.github.cookforher.exception.custom.EntityNotFoundException;
import com.github.cookforher.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppConfigTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private AppConfig appConfig;

  @Test
  void userDetailsService_shouldReturnUser_whenUserExists() {
    // Given
    User user = new User();
    user.setUsername("testUser");
    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

    // When
    UserDetailsService service = appConfig.userDetailsService();
    UserDetails result = service.loadUserByUsername("testUser");

    // Then
    assertEquals("testUser", result.getUsername());
  }

  @Test
  void userDetailsService_shouldThrowException_whenUserNotFound() {
    // Given
    when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

    // When
    UserDetailsService service = appConfig.userDetailsService();

    // Then
    assertThrows(EntityNotFoundException.class,
        () -> service.loadUserByUsername("nonexistent"));
  }

  @Test
  void authenticationManager_shouldReturnManager() throws Exception {
    // Given
    AuthenticationConfiguration config = mock(AuthenticationConfiguration.class);
    AuthenticationManager expectedManager = mock(AuthenticationManager.class);
    when(config.getAuthenticationManager()).thenReturn(expectedManager);

    // When
    AuthenticationManager result = appConfig.authenticationManager(config);

    // Then
    assertEquals(expectedManager, result);
  }

  @Test
  void passwordEncoder_shouldReturnBCryptEncoder() {
    // Given & When
    BCryptPasswordEncoder encoder = appConfig.passwordEncoder();

    // Then
    assertNotNull(encoder);
    assertTrue(encoder.matches("password", encoder.encode("password")));
  }
}
