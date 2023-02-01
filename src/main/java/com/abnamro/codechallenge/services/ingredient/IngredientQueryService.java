package com.abnamro.codechallenge.services.ingredient;

import java.util.List;
import java.util.Set;

import com.abnamro.codechallenge.domains.Ingredient;

public interface IngredientQueryService {

    List<Ingredient> fetchAllIngredients();

    Ingredient fetchIngredientById(Integer id);

    Ingredient fetchIngredientByName(String name);

    Set<Ingredient> fetchAllIngredientByIds(List<Integer> ids);
}
