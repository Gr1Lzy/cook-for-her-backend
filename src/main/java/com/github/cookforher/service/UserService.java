package com.github.cookforher.service;

import com.github.cookforher.dto.UserResponseDto;

public interface UserService {

  UserResponseDto getUserByUsername(String username);
}
