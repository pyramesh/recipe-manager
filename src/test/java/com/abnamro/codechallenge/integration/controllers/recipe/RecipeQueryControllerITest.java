package com.abnamro.codechallenge.integration.controllers.recipe;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.request.CreateRecipeRequest;
import com.abnamro.codechallenge.dto.response.RecipeDTO;
import com.abnamro.codechallenge.integration.controllers.AbstractControllerIntegrationTest;
import com.abnamro.codechallenge.repositories.IngredientRepository;
import com.abnamro.codechallenge.repositories.RecipeRepository;
import com.abnamro.codechallenge.search.RecipeSearchRequest;
import com.abnamro.codechallenge.search.SearchCriteriaRequest;

public class RecipeQueryControllerITest extends AbstractControllerIntegrationTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void before() {
        ingredientRepository.deleteAll();
        recipeRepository.deleteAll();
    }
    @Test
    public void testFetchRecipeById() throws Exception {
        Recipe Recipe = createRecipe();
        Recipe savedRecipe = recipeRepository.save(Recipe);

        performGet("/v1/recipes/" + savedRecipe.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedRecipe.getId()))
                .andExpect(jsonPath("$.name").value(savedRecipe.getName()))
                .andExpect(jsonPath("$.instructions").value(savedRecipe.getInstructions()))
                .andExpect(jsonPath("$.numberOfServings").value(savedRecipe.getNumberOfServings()));
    }

    @Test
    public void testFetchRecipeById_notFound() throws Exception {

        performGet("/v1/recipes/111")
                .andExpect(status().isNotFound());
    }

    @Test
    public void fetchAllRecipes() throws Exception {
        recipeRepository.deleteAll();
        Recipe recipe1 = Recipe.builder().name("recipe_name1")
                .instructions("instructions1")
                .isVegetarian(true)
                .numberOfServings(4)
                .build();

        Recipe recipe2 = Recipe.builder().name("recipe_name2")
                .instructions("instructions2")
                .isVegetarian(false)
                .numberOfServings(4)
                .build();
        List<Recipe> recipes = new ArrayList<>(Arrays.asList(recipe1, recipe2));

        recipeRepository.saveAll(recipes);

        MvcResult result = performGet("/v1/recipes")
                .andExpect(status().isOk())
                .andReturn();

        List<RecipeDTO> RecipeList = getListFromMvcResult(result, RecipeDTO.class);

        assertEquals(recipes.size(), RecipeList.size());
        assertEquals(recipes.get(0).getName(), RecipeList.get(0).getName());
        assertEquals(recipes.get(1).getName(), RecipeList.get(1).getName());
        assertEquals(recipes.get(1).getIsVegetarian(), RecipeList.get(1).getIsVegetarian());
    }
    //@Test
    public void testSearchRecipe() throws Exception {
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
        // 1. create ingredient
        Ingredient ingredient = Ingredient.builder()
                .name("tomatoes")
                .build();
        Ingredient savedIngredient = ingredientRepository.saveAndFlush(ingredient);

        //2. create the recipe
        CreateRecipeRequest createRecipeRequest =
                CreateRecipeRequest.builder()
                        .name("pasta")
                        .isVegetarian(true)
                        .instructions("instructions")
                        .ingredients(new HashSet<>(savedIngredient.getId()))
                        .build();



        MvcResult createdRecipe = performPost("/v1/recipes", createRecipeRequest)
                .andExpect(status().isCreated())
                .andReturn();

        Integer id = readByJsonPath(createdRecipe, "$.id");

        //  3. prepare search criteria for search operation
        RecipeSearchRequest request = new RecipeSearchRequest();
        List<SearchCriteriaRequest> searchCriteriaList = new ArrayList<>();
        SearchCriteriaRequest nameCriteria = new SearchCriteriaRequest("name",
                "pizza",
                "cn");

        searchCriteriaList.add(nameCriteria);

        request.setDataOption("ALL");
        request.setSearchCriteriaRequests(searchCriteriaList);

        //4. call endpoint
        MvcResult result = performPost("/v1/recipes/search", request)
                .andExpect(status().isOk())
                .andReturn();

        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);


        List<RecipeDTO> listRecipeList = getListFromMvcResult(result, RecipeDTO.class);
        assertEquals(listRecipeList.size(), listRecipeList.size());
        Assert.assertTrue(optionalRecipe.isPresent());
        assertEquals(listRecipeList.get(0).getName(), optionalRecipe.get().getName());
        assertEquals(listRecipeList.get(0).getInstructions(), optionalRecipe.get().getInstructions());
        assertEquals(listRecipeList.get(0).getNumberOfServings(), optionalRecipe.get().getNumberOfServings());
    }

    @Test
    public void test_SearchRecipeByCriteria_fails() throws Exception {
        performPost("/v1/recipes/search", null)
                .andExpect(status().is5xxServerError())
                .andReturn();
    }
}
