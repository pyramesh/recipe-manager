package com.abnamro.codechallenge.controllers.recipe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.request.CreateRecipeRequest;
import com.abnamro.codechallenge.dto.request.UpdateRecipeRequest;
import com.abnamro.codechallenge.dto.response.RecipeDTO;
import com.abnamro.codechallenge.services.recipe.RecipeCommandService;

@RunWith(MockitoJUnitRunner.class)
public class RecipeCommandControllerTest {

    @Mock
    private RecipeCommandService recipeCommandService;

    @InjectMocks
    private RecipeCommandController  recipeCommandController;

    @Test
    public void testCreateRecipe(){
        CreateRecipeRequest request = CreateRecipeRequest.builder()
                .name("recipe_name")
                .isVegetarian(Boolean.TRUE)
                .numberOfServings(5)
                .build();
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setName("ingredient_name");
        ingredient.setId(1);
        ingredients.add(ingredient);

        Recipe mockRecipe = Recipe.builder()
                .id(1)
                .name("recipe_name")
                .isVegetarian(Boolean.TRUE)
                .ingredients(ingredients)
                .build();

        when(recipeCommandService.createRecipe(any(CreateRecipeRequest.class))).thenReturn(mockRecipe);
        ResponseEntity<RecipeDTO> recipe = recipeCommandController.createRecipe(request);
        assertThat(recipe).isNotNull();
        assertThat(recipe.getBody().getId()).isSameAs(1);
    }


    @Test
    public void testUpdateRecipe(){
        Recipe recipe = Recipe.builder()
                .id(2)
                .name("recipe_name")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions")
                .build();
        when(recipeCommandService.updateRecipe(any(), any())).thenReturn(recipe);
        recipe.setName("recipe_name_update");

        UpdateRecipeRequest request = new UpdateRecipeRequest("vegetable curry", "spicy",Boolean.TRUE, 10, null);

        ResponseEntity<RecipeDTO> recipeDTOResponseEntity = recipeCommandController.updateRecipe(recipe.getId(), request);

        assertThat(recipe).isNotNull();
        assertThat(recipeDTOResponseEntity.getBody().getName()).isSameAs(recipe.getName());
    }

    @Test
    public void testDeleteRecipe(){
        doNothing().when(recipeCommandService).deleteRecipeById(any());
        recipeCommandController.deleteRecipeById(2);
    }

    @Test
    public void testDeleteAllRecipes(){
        doNothing().when(recipeCommandService).deleteAllRecipes();
        recipeCommandController.deleteAllRecipes();
    }
}
