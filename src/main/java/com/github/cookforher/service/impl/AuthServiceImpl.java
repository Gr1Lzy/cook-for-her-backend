package com.github.cookforher.service.impl;

import com.github.cookforher.dto.auth.LoginRequestDto;
import com.github.cookforher.dto.auth.LoginResponseDto;
import com.github.cookforher.dto.auth.RefreshRequestDto;
import com.github.cookforher.dto.auth.RegisterRequestDto;
import com.github.cookforher.entity.User;
import com.github.cookforher.exception.custom.AuthenticationException;
import com.github.cookforher.exception.custom.EntityExistsException;
import com.github.cookforher.exception.custom.EntityNotFoundException;
import com.github.cookforher.exception.custom.InvalidTokenException;
import com.github.cookforher.repository.UserRepository;
import com.github.cookforher.service.AuthService;
import com.github.cookforher.util.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final JwtTokenUtil jwtTokenUtil;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  @Override
  public LoginResponseDto login(LoginRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = requestDto.getPassword();

    Authentication authentication = tryToAuthenticate(username, password);
    User user = (User) authentication.getPrincipal();

    return LoginResponseDto.builder()
        .accessToken(jwtTokenUtil.generateAccessToken(user))
        .refreshToken(jwtTokenUtil.generateRefreshToken(user))
        .build();
  }

  @Override
  public void register(RegisterRequestDto requestDto) {
    User user = requestDto.toEntity();
    String password = requestDto.getPassword();
    String encodedPassword = passwordEncoder.encode(password);
    user.setPassword(encodedPassword);

    if (userRepository.existsByUsername(user.getUsername())) {
      throw new EntityExistsException("Username already exists");
    }

    if (userRepository.existsByEmail(user.getEmail())) {
      throw new EntityExistsException("Email already exists");
    }

    userRepository.save(user);
  }

  @Override
  public LoginResponseDto refresh(RefreshRequestDto requestDto) {
    String refreshToken = requestDto.getRefreshToken();

    if (!jwtTokenUtil.isRefreshTokenValid(refreshToken)) {
      throw new InvalidTokenException("Invalid or expired refresh token");
    }

    User user = userRepository.findById(jwtTokenUtil.extractUserId(refreshToken))
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return LoginResponseDto.builder()
        .accessToken(jwtTokenUtil.generateAccessToken(user))
        .refreshToken(jwtTokenUtil.generateRefreshToken(user))
        .build();
  }

  private Authentication tryToAuthenticate(String username, String password) {
    try {
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (Exception ex) {
      throw new AuthenticationException("Invalid username or password");
    }
  }
}
