package com.github.cookforher.repository;

import com.github.cookforher.entity.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

  @EntityGraph(attributePaths = "createdBy")
  Optional<Recipe> findByTitle(String title);
}
