package com.github.cookforher.services;

import com.github.cookforher.dto.auth.LoginRequestDto;
import com.github.cookforher.dto.auth.LoginResponseDto;
import com.github.cookforher.dto.auth.RefreshRequestDto;
import com.github.cookforher.dto.auth.RegisterRequestDto;
import com.github.cookforher.entity.User;
import com.github.cookforher.repository.UserRepository;
import com.github.cookforher.service.impl.AuthServiceImpl;
import com.github.cookforher.util.jwt.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private JwtTokenUtil jwtTokenUtil;

  @Mock
  private AuthenticationManager authenticationManager;

  @InjectMocks
  private AuthServiceImpl authService;

  @Test
  void login_shouldReturnTokens_whenValidCredentials() {
    // Given
    LoginRequestDto requestDto = new LoginRequestDto();
    requestDto.setUsername("testuser");
    requestDto.setPassword("password");

    // When
    User user = new User();
    Authentication authentication = mock(Authentication.class);
    when(authentication.getPrincipal()).thenReturn(user);
    when(authenticationManager.authenticate(any())).thenReturn(authentication);
    when(jwtTokenUtil.generateAccessToken(user)).thenReturn("access-token");
    when(jwtTokenUtil.generateRefreshToken(user)).thenReturn("refresh-token");

    LoginResponseDto result = authService.login(requestDto);

    // Then
    assertThat(result.getAccessToken()).isEqualTo("access-token");
    assertThat(result.getRefreshToken()).isEqualTo("refresh-token");
  }

  @Test
  void register_shouldSaveUser_whenValidData() {
    // Given
    RegisterRequestDto requestDto = new RegisterRequestDto();
    requestDto.setUsername("test");
    requestDto.setEmail("test@example.com");

    when(userRepository.existsByUsername("test")).thenReturn(false);
    when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

    // When
    authService.register(requestDto);

    // Then
    verify(userRepository).save(any(User.class));
  }

  @Test
  void refresh_shouldReturnLoginResponseDto_whenValidToken() {
    // Given
    RefreshRequestDto requestDto = new RefreshRequestDto();
    requestDto.setRefreshToken("valid-token");

    User user = new User();
    when(jwtTokenUtil.isRefreshTokenValid("valid-token")).thenReturn(true);
    when(jwtTokenUtil.extractUserId("valid-token")).thenReturn(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(jwtTokenUtil.generateAccessToken(user)).thenReturn("new-access");
    when(jwtTokenUtil.generateRefreshToken(user)).thenReturn("new-refresh");

    // When
    LoginResponseDto result = authService.refresh(requestDto);

    // Then
    assertThat(result.getAccessToken()).isEqualTo("new-access");
    assertThat(result.getRefreshToken()).isEqualTo("new-refresh");
  }
}


