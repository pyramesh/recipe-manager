package com.abnamro.codechallenge.integration.controllers.ingredient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.dto.request.CreateIngredientRequest;
import com.abnamro.codechallenge.integration.controllers.AbstractControllerIntegrationTest;
import com.abnamro.codechallenge.repositories.IngredientRepository;

public class IngredientCommandControllerITest extends AbstractControllerIntegrationTest {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Before
    public void before() {
        ingredientRepository.deleteAll();
    }


    @Test
    public void testCreateIngredient() throws Exception {
        CreateIngredientRequest request = createIngredientRequest();

        MvcResult result = performPost("/v1/ingredients", request)
                .andExpect(status().isCreated())
                .andReturn();

        Integer id = readByJsonPath(result, "$.id");

        Optional<Ingredient> ingredient = ingredientRepository.findById(id);

        assertTrue(ingredient.isPresent());
        assertEquals(ingredient.get().getName(), request.getName());
    }

    @Test
    public void testDeleteIngredientsById() throws Exception {
        Ingredient ingredient = createIngredient();
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        performDelete("/v1/ingredients/" + savedIngredient.getId())
                .andExpect(status().isOk());

        Optional<Ingredient> deletedIngredient = ingredientRepository.findById(savedIngredient.getId());
        assertFalse(deletedIngredient.isPresent());
    }

    @Test
    public void testDeleteIngredientsById_notFound() throws Exception {
        performDelete("/v1/ingredients/1")
                .andExpect(status().isNotFound());
    }


}
