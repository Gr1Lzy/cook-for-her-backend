package com.github.cookforher.entity;

import com.github.cookforher.dto.user.UserResponseDto;
import com.github.cookforher.mapper.DtoMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {

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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  public UserResponseDto toDto() {
    return MAPPER.toDto(this);
  }

  private static final DtoMapper<User, UserResponseDto> MAPPER =
      new DtoMapper<>(User.class, UserResponseDto.class);
}
