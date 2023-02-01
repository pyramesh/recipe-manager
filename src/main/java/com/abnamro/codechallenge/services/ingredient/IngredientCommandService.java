package com.abnamro.codechallenge.services.ingredient;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.dto.request.CreateIngredientRequest;
import com.abnamro.codechallenge.dto.request.UpdateIngredientRequest;

public interface IngredientCommandService {

    Ingredient createIngredient(CreateIngredientRequest createIngredientRequest);

    Ingredient updateIngredient(Integer ingredientId, UpdateIngredientRequest updateIngredientRequest);

    void deleteIngredientById(Integer id);

    void deleteAllIngredients();
}
