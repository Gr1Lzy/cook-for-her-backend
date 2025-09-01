package com.github.cookforher.dto.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecipeResponseDto {

  private String title;

  private String description;

  @JsonProperty("created_by_name")
  private String createdByName;

  @JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("updated_at")
  private String updatedAt;
}
