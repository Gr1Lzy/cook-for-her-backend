package com.github.cookforher.controller;

import com.github.cookforher.dto.auth.LoginRequestDto;
import com.github.cookforher.dto.auth.LoginResponseDto;
import com.github.cookforher.service.AuthService;
import com.github.cookforher.util.jwt.JwtAuthFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private JwtAuthFilter jwtAuthFilter;

  @MockitoBean
  private AuthService authService;

  @Test
  void login_shouldReturnLoginResponseDto() throws Exception {
    LoginResponseDto loginResponseDto = LoginResponseDto.builder()
        .accessToken("access")
        .refreshToken("refresh")
        .build();

    when(authService.login(any(LoginRequestDto.class))).thenReturn(loginResponseDto);

    mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"test\", \"password\": \"12345678\"}"))
        .andDo(print())
        .andExpect(status().isOk());
  }
}

