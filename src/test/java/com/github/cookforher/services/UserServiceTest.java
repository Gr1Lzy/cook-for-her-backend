package com.github.cookforher.services;

import com.github.cookforher.dto.user.UserResponseDto;
import com.github.cookforher.entity.User;
import com.github.cookforher.repository.UserRepository;
import com.github.cookforher.service.impl.UserServiceImpl;
import com.github.cookforher.util.user.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void getCurrentUser_shouldReturnUserDto_whenUserExists() {
    // Given
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("test@example.com");

    try (MockedStatic<UserUtil> userUtilMock = mockStatic(UserUtil.class)) {
      userUtilMock.when(UserUtil::getCurrentUserId).thenReturn(1L);
      when(userRepository.findById(1L)).thenReturn(Optional.of(user));

      // When
      UserResponseDto result = userService.getCurrentUser();

      // Then
      assertThat(result.getUsername()).isEqualTo("testuser");
      assertThat(result.getEmail()).isEqualTo("test@example.com");
    }
  }

  @Test
  void getCurrentUser_shouldThrowException_whenUserNotFound() {
    // Given
    try (MockedStatic<UserUtil> userUtilMock = mockStatic(UserUtil.class)) {
      userUtilMock.when(UserUtil::getCurrentUserId).thenReturn(1L);
      when(userRepository.findById(1L)).thenReturn(Optional.empty());

      // When & Then
      assertThatThrownBy(() -> userService.getCurrentUser())
          .isInstanceOf(EntityNotFoundException.class)
          .hasMessage("User not found");
    }
  }

  @Test
  void getUserByUsername_shouldReturnUserDto_whenUserExists() {
    // Given
    User user = new User();
    user.setUsername("testuser");
    user.setEmail("test@example.com");
    when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

    // When
    UserResponseDto result = userService.getUserByUsername("testuser");

    // Then
    assertThat(result.getUsername()).isEqualTo("testuser");
    assertThat(result.getEmail()).isEqualTo("test@example.com");
  }

  @Test
  void getUserByUsername_shouldThrowException_whenUserNotFound() {
    // Given
    when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

    // When & Then
    assertThatThrownBy(() -> userService.getUserByUsername("nonexistent"))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("User not found");
  }
}

