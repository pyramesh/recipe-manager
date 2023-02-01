package com.abnamro.codechallenge.integration.controllers.recipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.request.CreateRecipeRequest;
import com.abnamro.codechallenge.integration.controllers.AbstractControllerIntegrationTest;
import com.abnamro.codechallenge.repositories.RecipeRepository;

public class RecipeCommandControllerITest extends AbstractControllerIntegrationTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testCreateRecipe() throws Exception {
        CreateRecipeRequest request =
                CreateRecipeRequest.builder()
                        .name("pizza")
                        .isVegetarian(true)
                        .instructions("spicy")
                        .build();

        MvcResult result = performPost("/v1/recipes", request)
                .andExpect(status().isCreated())
                .andReturn();

        Integer recipeId = readByJsonPath(result, "$.id");
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        assertTrue(optionalRecipe.isPresent());
        assertEquals(optionalRecipe.get().getName(), request.getName());
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        Recipe recipe = Recipe.builder()
                .name("tomato soup")
                .numberOfServings(2)
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions")
                .build();

        Recipe savedRecipe = recipeRepository.save(recipe);

        savedRecipe.setName("tomato soup");
        savedRecipe.setInstructions("add pepper");

        performPut("/v1/recipes/"+savedRecipe.getId(), savedRecipe)
                .andExpect(status().isOk());

        Optional<Recipe> updatedRecipe = recipeRepository.findById(savedRecipe.getId());

        assertTrue(updatedRecipe.isPresent());
        assertEquals(savedRecipe.getName(), updatedRecipe.get().getName());
        assertEquals(savedRecipe.getNumberOfServings(), updatedRecipe.get().getNumberOfServings());
        assertEquals(savedRecipe.getInstructions(), updatedRecipe.get().getInstructions());
    }

    @Test
    public void testUpdateRecipeWhenIdDoesNotExists() throws Exception {
        Recipe recipe = Recipe.builder()
                .name("pasta")
                .numberOfServings(2)
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions")
                .build();

        performPut("/v1/recipes/0", recipe)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_deleteRecipe_notFound() throws Exception {
        recipeRepository.deleteAll();
        performDelete("/v1/recipes/1")
                .andExpect(status().isNotFound());
    }
    @Test
    public void testDeleteRecipeById() throws Exception {
        Recipe recipe = Recipe.builder()
                .name("tomato soup")
                .numberOfServings(10)
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions")
                .build();
        Recipe savedRecipe = recipeRepository.save(recipe);

        performDelete("/v1/recipes", Pair.of("id", String.valueOf(savedRecipe.getId())))
                .andExpect(status().isOk());

        Optional<Recipe> deletedRecipe = recipeRepository.findById(savedRecipe.getId());

        assertFalse(deletedRecipe.isPresent());
    }
}
