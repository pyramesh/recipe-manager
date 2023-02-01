package com.abnamro.codechallenge.services.ingredient;


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

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.dto.request.CreateIngredientRequest;
import com.abnamro.codechallenge.dto.request.UpdateIngredientRequest;
import com.abnamro.codechallenge.exceptions.IngredientNotFoundException;
import com.abnamro.codechallenge.repositories.IngredientRepository;

@RunWith(MockitoJUnitRunner.class)
public class IngredientCommandServiceTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientCommandServiceImpl ingredientCommandService;


    @Test
    public void testCreateIngredient(){
        CreateIngredientRequest request = CreateIngredientRequest.builder().name("ingredient_name").build();
        Ingredient ingredient = Ingredient.builder().id(1).name("ingredient_name").build();

        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);
        Ingredient ingredientResponse = ingredientCommandService.createIngredient(request);

        assertThat(ingredientResponse.getId()).isSameAs(ingredient.getId());
    }


    @Test
    public void testUpdateRecipe(){
        Ingredient ingredientResponse = Ingredient.builder().id(1).name("fish").build();
        UpdateIngredientRequest request = new UpdateIngredientRequest("fish updated");

        when(ingredientRepository.existsById(anyInt())).thenReturn(true);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredientResponse);
        when(ingredientRepository.findById(anyInt())).thenReturn(Optional.of(ingredientResponse));

        ingredientCommandService.updateIngredient(ingredientResponse.getId(), request);
    }

    @Test
    public void testDeleteRecipeById(){
        when(ingredientRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(ingredientRepository).deleteById(anyInt());
        ingredientCommandService.deleteIngredientById(1);
    }

    @Test(expected = IngredientNotFoundException.class)
    public void testDeleteRecipeById_when_Id_Not_Found() {
        when(ingredientRepository.existsById(anyInt())).thenReturn(false);
        ingredientCommandService.deleteIngredientById(1);
    }
    @Test
    public void testDeleteAllRecipes(){
        ingredientCommandService.deleteAllIngredients();
    }
}
