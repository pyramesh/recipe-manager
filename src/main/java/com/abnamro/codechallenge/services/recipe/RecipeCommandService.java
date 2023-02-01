package com.abnamro.codechallenge.services.recipe;

import org.springframework.stereotype.Service;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.request.CreateRecipeRequest;
import com.abnamro.codechallenge.dto.request.UpdateRecipeRequest;

@Service
public interface RecipeCommandService {

    void deleteRecipeById(Integer id);

    void deleteAllRecipes();

    Recipe updateRecipe(Integer recipeId, UpdateRecipeRequest updateRecipeRequest);

    Recipe createRecipe(CreateRecipeRequest createRecipeRequest);

}
