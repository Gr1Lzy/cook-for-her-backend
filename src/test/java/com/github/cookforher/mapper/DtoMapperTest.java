package com.github.cookforher.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DtoMapperTest {

  static class TestEntity {
    private String name;
    private int value;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }
  }

  static class TestDto {
    private String name;
    private int value;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }
  }

  @Test
  void toEntity_shouldMapDtoToEntity() {
    // Given
    DtoMapper<TestEntity, TestDto> mapper = new DtoMapper<>(TestEntity.class, TestDto.class);
    TestDto dto = new TestDto();
    dto.setName("test");
    dto.setValue(42);

    // When
    TestEntity result = mapper.toEntity(dto);

    // Then
    assertNotNull(result);
    assertEquals("test", result.getName());
    assertEquals(42, result.getValue());
  }

  @Test
  void toDto_shouldMapEntityToDto() {
    // Given
    DtoMapper<TestEntity, TestDto> mapper = new DtoMapper<>(TestEntity.class, TestDto.class);
    TestEntity entity = new TestEntity();
    entity.setName("test");
    entity.setValue(42);

    // When
    TestDto result = mapper.toDto(entity);

    // Then
    assertNotNull(result);
    assertEquals("test", result.getName());
    assertEquals(42, result.getValue());
  }
}

