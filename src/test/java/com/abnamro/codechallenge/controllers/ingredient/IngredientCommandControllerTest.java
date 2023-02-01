package com.abnamro.codechallenge.controllers.ingredient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.dto.request.CreateIngredientRequest;
import com.abnamro.codechallenge.dto.request.UpdateIngredientRequest;
import com.abnamro.codechallenge.dto.response.IngredientDTO;
import com.abnamro.codechallenge.services.ingredient.IngredientCommandService;

@RunWith(MockitoJUnitRunner.class)
public class IngredientCommandControllerTest {

    @Mock
    private IngredientCommandService ingredientCommandService;

    @InjectMocks
    private IngredientCommandController ingredientCommandController;

    @Test
    public void testCreateIngredient(){
        CreateIngredientRequest request = CreateIngredientRequest.builder().name("ingredient_name").build();
       Ingredient ingredient = Ingredient.builder().id(1).name("ingredient_name").build();

        when(ingredientCommandService.createIngredient(any(CreateIngredientRequest.class))).thenReturn(ingredient);
        ResponseEntity<IngredientDTO> response = ingredientCommandController.createIngredient(request);
        assertThat(response).isNotNull();
        assertThat(response.getBody().getId()).isSameAs(1);
        assertThat(response.getBody().getName()).isSameAs("ingredient_name");
    }


    @Test
    public void testUpdateIngredient(){
        Ingredient ingredient = Ingredient.builder().id(1).name("fish").build();

        when(ingredientCommandService.updateIngredient(anyInt(), any())).thenReturn(ingredient);
        ingredient.setName("fish_updated");

        UpdateIngredientRequest request = UpdateIngredientRequest.builder().name("fish_updated").build();
        IngredientDTO ingredientDTO = ingredientCommandController.updateIngredient(ingredient.getId(), request);
        assertThat(ingredientDTO.getName()).isSameAs(ingredient.getName());
    }

    @Test
    public void testDeleteIngredientById(){
        doNothing().when(ingredientCommandService).deleteIngredientById(any());
        ingredientCommandController.deleteIngredientById(2);
    }

    @Test
    public void testDeleteAllIngredients(){
        doNothing().when(ingredientCommandService).deleteAllIngredients();
        ingredientCommandController.deleteAllIngredients();
    }
}
