package com.abnamro.codechallenge.services.ingredient;

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

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.exceptions.IngredientNotFoundException;
import com.abnamro.codechallenge.repositories.IngredientRepository;

@RunWith(MockitoJUnitRunner.class)
public class IngredientQueryServiceTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientQueryServiceImpl ingredientQueryService;

    @Test
    public void testFetchIngredientById(){
        Ingredient ingredientResponse = Ingredient.builder().id(1).name("ingredient_name").build();
        when(ingredientRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(ingredientResponse));
        Ingredient ingredient = ingredientQueryService.fetchIngredientById(1);

        assertThat(ingredient.getId()).isSameAs(ingredientResponse.getId());
    }

    @Test
    public void testFetchIngredientById_When_Ingredient_Is_Not_Found(){
        when(ingredientRepository.findById(anyInt())).thenThrow(IngredientNotFoundException.class);
        assertThatThrownBy(() ->  ingredientQueryService.fetchIngredientById(0001))
                .isInstanceOf(IngredientNotFoundException.class);
    }

    @Test
    public void testFetchIngredients(){
        Ingredient ingredientResponse1 = Ingredient.builder().id(1).name("ingredient_name2").build();
        Ingredient ingredientResponse2 = Ingredient.builder().id(2).name("ingredient_name2").build();
        List<Ingredient> ingredientResponse = Arrays.asList(ingredientResponse1, ingredientResponse2);
        when(ingredientRepository.findAll()).thenReturn(ingredientResponse);
        List<Ingredient> ingredients = ingredientQueryService.fetchAllIngredients();

        assertThat(ingredients).isSameAs(ingredientResponse);
    }
}
