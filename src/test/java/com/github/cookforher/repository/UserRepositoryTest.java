package com.github.cookforher.repository;

import com.github.cookforher.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@EnableJpaAuditing
class UserRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  void findByUsername_shouldReturnUser_whenUserExists() {
    // Given
    User user = new User();
    user.setUsername("test");
    user.setEmail("test@example.com");
    user.setPassword("password");
    entityManager.persistAndFlush(user);

    // When
    Optional<User> result = userRepository.findByUsername("test");

    // Then
    assertThat(result).isPresent();
    assertThat(result.orElseThrow().getUsername()).isEqualTo("test");
  }

  @Test
  void findByUsername_shouldReturnEmpty_whenUserNotExists() {
    // Given
    // No user persisted

    // When
    Optional<User> result = userRepository.findByUsername("nonexistent");

    // Then
    assertThat(result).isEmpty();
  }

  @Test
  void existsByEmail_shouldReturnTrue_whenEmailExists() {
    // Given
    User user = new User();
    user.setUsername("test");
    user.setEmail("test@example.com");
    user.setPassword("password");
    entityManager.persistAndFlush(user);

    // When
    boolean exists = userRepository.existsByEmail("test@example.com");

    // Then
    assertThat(exists).isTrue();
  }

  @Test
  void existsByEmail_shouldReturnFalse_whenEmailNotExists() {
    // Given
    // No user persisted

    // When
    boolean exists = userRepository.existsByEmail("nonexistent@example.com");

    // Then
    assertThat(exists).isFalse();
  }
}

