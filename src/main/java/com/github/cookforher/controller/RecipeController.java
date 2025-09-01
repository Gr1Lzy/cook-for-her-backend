package com.github.cookforher.controller;

import com.github.cookforher.dto.recipe.RecipeRequestDto;
import com.github.cookforher.dto.recipe.RecipeResponseDto;
import com.github.cookforher.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Recipe Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/recipe")
public class RecipeController {

  private final RecipeService recipeService;

  @Operation(summary = "Create recipe")
  @PostMapping("/create")
  public ResponseEntity<String> createRecipe(@RequestBody @Valid RecipeRequestDto requestDto) {
    recipeService.createRecipe(requestDto);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "Get recipe by title")
  @PostMapping("/{title}")
  public ResponseEntity<RecipeResponseDto> getRecipeByTitle(@PathVariable String title) {
    return ResponseEntity.ok(recipeService.getRecipeByTitle(title));
  }
}
