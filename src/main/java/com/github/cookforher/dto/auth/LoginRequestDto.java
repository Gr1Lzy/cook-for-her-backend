package com.github.cookforher.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

  @Schema(example = "admin")
  @NotBlank(message = "Username is required.")
  private String username;

  @Schema(example = "12345678")
  @NotBlank(message = "Password is required.")
  private String password;
}
