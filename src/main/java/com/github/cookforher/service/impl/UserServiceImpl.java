package com.github.cookforher.service.impl;

import com.github.cookforher.dto.user.UserResponseDto;
import com.github.cookforher.entity.User;
import com.github.cookforher.repository.UserRepository;
import com.github.cookforher.service.UserService;
import com.github.cookforher.util.user.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserResponseDto getCurrentUser() {
    Long userId = UserUtil.getCurrentUserId();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return user.toDto();
  }

  @Override
  public UserResponseDto getUserByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return user.toDto();
  }
}
