package com.github.cookforher.dto.recipe;

import com.github.cookforher.entity.Recipe;
import com.github.cookforher.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecipeRequestDto {

  @Schema(example = "title")
  @NotBlank(message = "Title is required.")
  private String title;

  @Schema(example ="description")
  @NotBlank(message = "Description is required.")
  private String description;

  public Recipe toEntity() {
    return MAPPER.toEntity(this);
  }

  private static final DtoMapper<Recipe, RecipeRequestDto> MAPPER =
      new DtoMapper<>(Recipe.class, RecipeRequestDto.class);
}
