package com.github.cookforher.service;

import com.github.cookforher.dto.user.UserResponseDto;

public interface UserService {

  UserResponseDto getUserByUsername(String username);
}
