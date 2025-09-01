package com.github.cookforher.controller;

import com.github.cookforher.dto.auth.LoginRequestDto;
import com.github.cookforher.dto.auth.LoginResponseDto;
import com.github.cookforher.dto.auth.RefreshRequestDto;
import com.github.cookforher.dto.auth.RegisterRequestDto;
import com.github.cookforher.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "User login")
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto) {
    return ResponseEntity.ok(authService.login(requestDto));
  }

  @Operation(summary = "User register")
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto requestDto) {
    authService.register(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "User refresh token")
  @PostMapping("/refresh")
  public ResponseEntity<LoginResponseDto> refresh(@RequestBody @Valid RefreshRequestDto requestDto) {
    return ResponseEntity.ok(authService.refresh(requestDto));
  }
}