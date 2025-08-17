package com.github.cookforher.dto.auth;

import com.github.cookforher.entity.User;
import com.github.cookforher.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {

  @Schema(example = "test")
  @NotBlank(message = "Username is required.")
  private String username;

  @Schema(example = "text@test.com")
  @NotBlank(message = "Email is required.")
  @Email(message = "Wrong email format.")
  private String email;

  @Schema(example = "12345678")
  @NotBlank(message = "Password is required.")
  @Size(min = 8, message = "Password must be at least 8 characters.")
  private String password;

  public User toEntity() {
    return MAPPER.toEntity(this);
  }

  private static final DtoMapper<User, RegisterRequestDto> MAPPER =
      new DtoMapper<>(User.class,  RegisterRequestDto.class);
}
