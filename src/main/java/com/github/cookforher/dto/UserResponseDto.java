package com.github.cookforher.dto;

import lombok.Data;

@Data
public class UserResponseDto {
  private String username;
  private String email;
  private String role;
}
