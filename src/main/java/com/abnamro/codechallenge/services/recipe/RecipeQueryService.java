package com.abnamro.codechallenge.services.recipe;

import java.util.List;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.search.RecipeSearchRequest;

public interface RecipeQueryService {

    List<Recipe> fetchAllRecipes();

    Recipe fetchRecipeById(Integer id);

    List<Recipe> search(RecipeSearchRequest recipeSearchRequest, int page, int size);
}

