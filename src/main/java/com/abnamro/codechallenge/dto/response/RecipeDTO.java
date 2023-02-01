package com.abnamro.codechallenge.dto.response;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.abnamro.codechallenge.domains.Recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private Integer id;
    private String name;
    private String instructions;
    private Boolean isVegetarian;
    private int numberOfServings;
    private Set<IngredientDTO> ingredients;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RecipeDTO(final Recipe recipe) {
        id = recipe.getId();
        name = recipe.getName();
        isVegetarian = recipe.getIsVegetarian();
        instructions = recipe.getInstructions();
        createdAt = recipe.getCreatedAt();
        updatedAt = recipe.getUpdatedAt();
        numberOfServings = recipe.getNumberOfServings();
        ingredients = recipe.getIngredients() != null ?
                recipe.getIngredients().stream()
                        .map(IngredientDTO::new)
                        .collect(Collectors.toSet())
                : null;
    }
}
