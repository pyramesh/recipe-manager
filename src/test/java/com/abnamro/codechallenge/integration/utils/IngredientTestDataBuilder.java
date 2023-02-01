package com.abnamro.codechallenge.integration.utils;

import com.abnamro.codechallenge.domains.Ingredient;

public class IngredientTestDataBuilder {

    public static Ingredient createIngredient() {
        return Ingredient.builder()
                .name("potatoes")
                .build();
    }
}
