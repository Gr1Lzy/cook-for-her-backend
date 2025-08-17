package com.github.cookforher.util.role;

import com.github.cookforher.entity.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Role role) {
    return role != null
        ? role.getId()
        : null;
  }

  @Override
  public Role convertToEntityAttribute(Integer dbData) {
    return dbData != null
        ? Role.fromId(dbData)
        : null;
  }
}
