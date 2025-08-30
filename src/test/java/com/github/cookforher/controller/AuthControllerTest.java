package com.github.cookforher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cookforher.dto.auth.LoginRequestDto;
import com.github.cookforher.dto.auth.LoginResponseDto;
import com.github.cookforher.dto.auth.RefreshRequestDto;
import com.github.cookforher.dto.auth.RegisterRequestDto;
import com.github.cookforher.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private AuthService authService;

  @Test
  void login_shouldReturnLoginResponseDto() throws Exception {
    // Given
    LoginRequestDto loginRequestDto = new LoginRequestDto();
    loginRequestDto.setUsername("test");
    loginRequestDto.setPassword("12345678");

    LoginResponseDto loginResponseDto = LoginResponseDto.builder()
        .accessToken("access")
        .refreshToken("refresh")
        .build();

    // When
    when(authService.login(any(LoginRequestDto.class))).thenReturn(loginResponseDto);

    // Then
    mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequestDto)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void register_shouldReturnCreated() throws Exception {
    // Given
    RegisterRequestDto registerRequestDto = new RegisterRequestDto();
    registerRequestDto.setUsername("test");
    registerRequestDto.setPassword("12345678");
    registerRequestDto.setEmail("test@example.com");

    // When
    doNothing().when(authService).register(any(RegisterRequestDto.class));

    // Then
    mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerRequestDto)))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  void refresh_shouldReturnLoginResponseDto() throws Exception {
    // Given
    RefreshRequestDto refreshRequestDto = new RefreshRequestDto();
    refreshRequestDto.setRefreshToken("refresh-token");

    LoginResponseDto loginResponseDto = LoginResponseDto.builder()
        .accessToken("new-access")
        .refreshToken("new-refresh")
        .build();

    // When
    when(authService.refresh(any(RefreshRequestDto.class))).thenReturn(loginResponseDto);

    // Then
    mockMvc.perform(post("/api/auth/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(refreshRequestDto)))
        .andDo(print())
        .andExpect(status().isOk());
  }
}

