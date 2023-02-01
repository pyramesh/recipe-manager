package com.abnamro.codechallenge.services.recipe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.exceptions.RecipeNotFoundException;
import com.abnamro.codechallenge.repositories.RecipeRepository;

@RunWith(MockitoJUnitRunner.class)
public class RecipeQueryServiceTest {
    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeQueryServiceImpl recipeQueryService;

    @Test
    public void testFetchRecipeById(){
        Recipe recipeResponse = Recipe
                .builder()
                .id(1)
                .name("recipe_name")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions")
                .build();

        when(recipeRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(recipeResponse));
        Recipe recipe = recipeQueryService.fetchRecipeById(1);

        assertThat(recipe.getId()).isSameAs(recipeResponse.getId());
    }

    @Test
    public void testFetchIngredientById_When_Recipe_Is_Not_Found(){
        when(recipeRepository.findById(anyInt())).thenThrow(RecipeNotFoundException.class);
        assertThatThrownBy(() ->  recipeQueryService.fetchRecipeById(0001))
                .isInstanceOf(RecipeNotFoundException.class);
    }

    @Test
    public void testFetchIngredients(){
        Recipe recipeResponse1 = Recipe
                .builder()
                .id(1)
                .name("recipe_name1")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions1")
                .build();
        Recipe recipeResponse2 = Recipe
                .builder()
                .id(2)
                .name("recipe_name2")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions1")
                .build();
        List<Recipe> recipeResponse = Arrays.asList(recipeResponse1, recipeResponse2);
        when(recipeRepository.findAll()).thenReturn(recipeResponse);
        List<Recipe> ingredients = recipeQueryService.fetchAllRecipes();

        assertThat(ingredients).isSameAs(recipeResponse);
    }
}
