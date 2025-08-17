package com.github.cookforher.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

  @JsonProperty(namespace = "access_token")
  private String accessToken;

  @JsonProperty(namespace = "refresh_token")
  private String refreshToken;
}
