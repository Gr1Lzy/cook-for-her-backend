package com.github.cookforher.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JpaConfigTest {

  @Test
  void jpaConfig_shouldBeCreated() {
    // When
    JpaConfig config = new JpaConfig();

    // Then
    assertNotNull(config);
  }
}
