package com.github.cookforher.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshRequestDto {

  @NotBlank(message = "Refresh Token is required.")
  @JsonProperty(namespace = "refresh_token")
  private String refreshToken;
}
