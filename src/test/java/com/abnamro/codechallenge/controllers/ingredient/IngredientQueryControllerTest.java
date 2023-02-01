package com.abnamro.codechallenge.controllers.ingredient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.exceptions.IngredientNotFoundException;
import com.abnamro.codechallenge.services.ingredient.IngredientQueryService;

@RunWith(MockitoJUnitRunner.class)
public class IngredientQueryControllerTest {
    @Mock
    private IngredientQueryService ingredientQueryService;

    @InjectMocks
    private IngredientQueryController ingredientQueryController;

    @Test
    public void testFetchRecipeById() {
        Ingredient ingredient = Ingredient.builder()
                .id(1)
                .name("fish")
                .build();

        when(ingredientQueryService.fetchIngredientById(anyInt())).thenReturn(ingredient);

        ResponseEntity<Ingredient> response = ingredientQueryController.fetchIngredientById(1);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getId()).isSameAs(1);
    }

    @Test
    public void testFetchRecipeById_when_IngredientNotFound() {
        when(ingredientQueryService.fetchIngredientById(anyInt()))
                .thenThrow(IngredientNotFoundException.class);
        assertThatThrownBy(() -> ingredientQueryController.fetchIngredientById(999))
                .isInstanceOf(IngredientNotFoundException.class);
    }

    @Test
    public void testFetchAllRecipes() {
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient1 = Ingredient.builder().id(1).name("fish").build();
        Ingredient ingredient2 = Ingredient.builder().id(2).name("chicken").build();
        ingredients.addAll(Arrays.asList(ingredient1, ingredient2));

        when(ingredientQueryService.fetchAllIngredients()).thenReturn(ingredients);

        ResponseEntity<List<Ingredient>> response = ingredientQueryController.fetchAllIngredients();

        assertThat(response).isNotNull();
        assertThat(response.getBody().get(0).getId()).isSameAs(1);
        assertThat(response.getBody().get(1).getId()).isSameAs(2);
        assertThat(response.getBody().get(0).getName()).isSameAs("fish");
        assertThat(response.getBody().get(1).getName()).isSameAs("chicken");
    }

    @Test
    public void testFetchAllIngredients_When_No_Ingredients(){
        when(ingredientQueryService.fetchAllIngredients()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Ingredient>> response = ingredientQueryController.fetchAllIngredients();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}
