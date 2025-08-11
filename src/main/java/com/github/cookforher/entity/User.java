package com.github.cookforher.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity {

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "role_id")
  private Role role;

  @PrePersist
  public void init() {
    role = Role.ROLE_USER;
  }
}
