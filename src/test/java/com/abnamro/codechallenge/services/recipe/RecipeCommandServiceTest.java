package com.abnamro.codechallenge.services.recipe;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.request.CreateRecipeRequest;
import com.abnamro.codechallenge.dto.request.UpdateRecipeRequest;
import com.abnamro.codechallenge.exceptions.RecipeNotFoundException;
import com.abnamro.codechallenge.repositories.RecipeRepository;

@RunWith(MockitoJUnitRunner.class)
public class RecipeCommandServiceTest {
    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeCommandServiceImpl recipeCommandService;


    @Test
    public void testCreateRecipe(){
        CreateRecipeRequest request = CreateRecipeRequest.builder()
                .name("recipe_name")
                .isVegetarian(Boolean.TRUE)
                .numberOfServings(5)
                .build();

        Recipe recipe = Recipe.builder()
                .id(1)
                .name("recipe_name")
                .isVegetarian(Boolean.TRUE)
                .build();

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        Recipe recipeResponse = recipeCommandService.createRecipe(request);

        assertThat(recipeResponse.getId()).isSameAs(recipe.getId());
    }


    @Test
    public void testUpdateRecipe(){
        Recipe recipeResponse = Recipe.builder()
                .id(2)
                .name("vegetable curry")
                .isVegetarian(Boolean.TRUE)
                .numberOfServings(11)
                .instructions("spicy")
                .build();

        UpdateRecipeRequest request = new UpdateRecipeRequest("vegetable curry", "spicy", Boolean.TRUE, 10, null);

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeResponse);
        when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(recipeResponse));

        recipeCommandService.updateRecipe(recipeResponse.getId(), request);
    }

    @Test
    public void testDeleteRecipeById(){
        when(recipeRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(recipeRepository).deleteById(anyInt());
        recipeCommandService.deleteRecipeById(1);
    }

    @Test(expected = RecipeNotFoundException.class)
    public void testDeleteRecipeById_when_Id_Not_Found() {
        when(recipeRepository.existsById(anyInt())).thenReturn(false);
        recipeCommandService.deleteRecipeById(1);
    }
    @Test
    public void testDeleteAllRecipes(){
        recipeCommandService.deleteAllRecipes();
    }
}
