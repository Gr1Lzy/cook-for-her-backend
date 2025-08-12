package com.github.cookforher.service;

import com.github.cookforher.dto.auth.LoginRequestDto;
import com.github.cookforher.dto.auth.LoginResponseDto;
import com.github.cookforher.dto.auth.RefreshRequestDto;
import com.github.cookforher.dto.auth.RegisterRequestDto;

public interface AuthService {

  LoginResponseDto login(LoginRequestDto requestDto);

  void register(RegisterRequestDto requestDto);

  LoginResponseDto refresh(RefreshRequestDto requestDto);
}
