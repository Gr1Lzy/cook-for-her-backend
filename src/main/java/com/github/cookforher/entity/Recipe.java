package com.github.cookforher.entity;

import com.github.cookforher.dto.recipe.RecipeResponseDto;
import com.github.cookforher.mapper.DtoMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "recipes")
public class Recipe extends AbstractEntity {

  @Column(name = "title", unique = true)
  private String title;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "created_by")
  private User createdBy;

  public RecipeResponseDto toDto() {
    RecipeResponseDto recipeDto = MAPPER.toDto(this);
    recipeDto.setCreatedByName(createdBy.getUsername());
    return recipeDto;
  }

  private static final DtoMapper<Recipe, RecipeResponseDto> MAPPER =
      new DtoMapper<>(Recipe.class, RecipeResponseDto.class);
}
