package com.github.cookforher.service;

import com.github.cookforher.dto.recipe.RecipeRequestDto;
import com.github.cookforher.dto.recipe.RecipeResponseDto;

public interface RecipeService {

  void createRecipe(RecipeRequestDto requestDto);

  RecipeResponseDto getRecipeByTitle(String title);
}
