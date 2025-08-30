package com.github.cookforher.entity;

import com.github.cookforher.dto.user.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

  @Test
  void init_shouldSetDefaultRole_whenPrePersist() {
    // Given
    User user = new User();

    // When
    user.init();

    // Then
    assertThat(user.getRole()).isEqualTo(Role.ROLE_USER);
  }

  @Test
  void getAuthorities_shouldReturnUserRole_whenCalled() {
    // Given
    User user = new User();
    user.setRole(Role.ROLE_USER);

    // When
    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

    // Then
    assertThat(authorities.iterator().next().getAuthority()).isEqualTo("ROLE_USER");
  }

  @Test
  void toDto_shouldReturnUserResponseDto_whenCalled() {
    // Given
    User user = new User();
    user.setUsername("test");
    user.setEmail("test@example.com");

    // When
    UserResponseDto dto = user.toDto();

    // Then
    assertThat(dto.getUsername()).isEqualTo("test");
    assertThat(dto.getEmail()).isEqualTo("test@example.com");
  }
}
