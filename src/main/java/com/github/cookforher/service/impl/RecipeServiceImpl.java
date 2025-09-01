package com.github.cookforher.service.impl;

import com.github.cookforher.dto.recipe.RecipeRequestDto;
import com.github.cookforher.dto.recipe.RecipeResponseDto;
import com.github.cookforher.entity.Recipe;
import com.github.cookforher.entity.User;
import com.github.cookforher.exception.custom.EntityNotFoundException;
import com.github.cookforher.repository.RecipeRepository;
import com.github.cookforher.repository.UserRepository;
import com.github.cookforher.service.RecipeService;
import com.github.cookforher.util.user.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

  private final RecipeRepository recipeRepository;
  private final UserRepository userRepository;

  @Override
  public void createRecipe(RecipeRequestDto requestDto) {
    Recipe recipe = requestDto.toEntity();
    User user = userRepository.getReferenceById(UserUtil.getCurrentUserId());

    recipe.setCreatedBy(user);

    if (recipeRepository.findByTitle(recipe.getTitle()).isPresent()) {
      throw new EntityNotFoundException("Recipe already exists");
    }

    recipeRepository.save(recipe);
  }

  @Override
  public RecipeResponseDto getRecipeByTitle(String title) {
    Recipe recipe = recipeRepository.findByTitle(title)
        .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

    return recipe.toDto();
  }
}
