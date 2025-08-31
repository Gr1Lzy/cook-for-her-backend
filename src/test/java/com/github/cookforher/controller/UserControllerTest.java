package com.github.cookforher.controller;

import com.github.cookforher.dto.user.UserResponseDto;
import com.github.cookforher.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private UserService userService;

  @Test
  void getUserByUsername_shouldReturnUserResponseDto_whenValidUsername() throws Exception {
    // Given
    String username = "testuser";
    UserResponseDto userResponseDto = new UserResponseDto();
    userResponseDto.setUsername(username);
    userResponseDto.setEmail("test@example.com");

    // When
    when(userService.getUserByUsername(username)).thenReturn(userResponseDto);

    // Then
    mockMvc.perform(get("/api/user/{username}", username))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void    getCurrentUser_shouldReturnUserResponseDto_whenAuthenticated() throws Exception {
    // Given
    UserResponseDto userResponseDto = new UserResponseDto();
    userResponseDto.setUsername("currentuser");
    userResponseDto.setEmail("current@example.com");

    // When
    when(userService.getCurrentUser()).thenReturn(userResponseDto);

    // Then
    mockMvc.perform(get("/api/user"))
        .andDo(print())
        .andExpect(status().isOk());
  }
}

