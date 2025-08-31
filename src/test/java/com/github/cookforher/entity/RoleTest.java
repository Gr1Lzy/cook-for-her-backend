package com.github.cookforher.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RoleTest {

  @Test
  void fromId_shouldReturnRoleAdmin_whenIdIs1() {
    // Given
    Integer id = 1;

    // When
    Role role = Role.fromId(id);

    // Then
    assertThat(role).isEqualTo(Role.ROLE_ADMIN);
  }

  @Test
  void fromId_shouldReturnRoleUser_whenIdIs2() {
    // Given
    Integer id = 2;

    // When
    Role role = Role.fromId(id);

    // Then
    assertThat(role).isEqualTo(Role.ROLE_USER);
  }

  @Test
  void fromId_shouldThrowException_whenInvalidId() {
    // Given
    Integer invalidId = 999;

    // When & Then
    assertThatThrownBy(() -> Role.fromId(invalidId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid role id: 999");
  }
}