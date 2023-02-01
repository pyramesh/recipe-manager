package com.abnamro.codechallenge.integration.controllers.ingredient;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.dto.response.IngredientDTO;
import com.abnamro.codechallenge.integration.controllers.AbstractControllerIntegrationTest;
import com.abnamro.codechallenge.repositories.IngredientRepository;

public class IngredientQueryControllerITest extends AbstractControllerIntegrationTest {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Before
    public void before() {
        ingredientRepository.deleteAll();
    }

    @Test
    public void testFetchIngredientById() throws Exception {
        Ingredient ingredient = createIngredient();
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        performGet("/v1/ingredients/" + savedIngredient.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedIngredient.getId()))
                .andExpect(jsonPath("$.name").value(ingredient.getName()));
    }

    @Test
    public void testFetchIngredientById_notFound() throws Exception {
        performGet("/v1/ingredients/1")
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFetchAllIngredients() throws Exception {

        List<Ingredient> ingredientList = new ArrayList<>();
        Ingredient ingredient1 = Ingredient.builder()
                .name("chicken")
                .build();

        Ingredient ingredient2 = Ingredient.builder()
                .name("Fish")
                .build();
        ingredientList.addAll(Arrays.asList(ingredient1, ingredient2));

        ingredientRepository.saveAll(ingredientList);

        MvcResult result = performGet("/v1/ingredients")
                .andExpect(status().isOk())
                .andReturn();

        List<IngredientDTO> responses = getListFromMvcResult(result, IngredientDTO.class);
        assertEquals(ingredientList.size(), responses.size());
    }

}
