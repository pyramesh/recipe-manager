package com.abnamro.codechallenge.controllers.recipe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.response.RecipeDTO;
import com.abnamro.codechallenge.exceptions.RecipeNotFoundException;
import com.abnamro.codechallenge.services.recipe.RecipeQueryService;

@RunWith(MockitoJUnitRunner.class)
public class RecipeQueryControllerTest {

    @Mock
    private RecipeQueryService recipeQueryService;

    @InjectMocks
    private RecipeQueryController  recipeQueryController;

    @Test
    public void testFetchRecipeById() {
        Recipe mockRecipe = Recipe
                .builder()
                .id(1)
                .name("recipe_name")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions")
                .build();

        when(recipeQueryService.fetchRecipeById(anyInt())).thenReturn(mockRecipe);
        ResponseEntity<RecipeDTO> recipe = recipeQueryController.fetchRecipeById(1);

        assertThat(recipe).isNotNull();
        assertThat(recipe.getBody().getId()).isSameAs(1);
    }

    @Test
    public void testFetchRecipeById_when_RecipeNotFound() {
        when(recipeQueryService.fetchRecipeById(anyInt()))
                .thenThrow(RecipeNotFoundException.class);
        assertThatThrownBy(() -> recipeQueryController.fetchRecipeById(8888))
                .isInstanceOf(RecipeNotFoundException.class);
    }

    @Test
    public void testFetchAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Recipe mockRecipe1 = Recipe.builder().id(1).name("recipe_name1")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions1")
                .build();

        Recipe mockRecipe2 = Recipe
                .builder()
                .id(2)
                .name("recipe_name2")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions2")
                .build();
        recipes.add(mockRecipe1);
        recipes.add(mockRecipe2);

        when(recipeQueryService.fetchAllRecipes()).thenReturn(recipes);

        ResponseEntity<List<RecipeDTO>> recipe = recipeQueryController.fetchAllRecipes();

        assertThat(recipe).isNotNull();
        assertThat(recipe.getBody().get(0).getId()).isSameAs(1);
        assertThat(recipe.getBody().get(1).getId()).isSameAs(2);
        assertThat(recipe.getBody().get(0).getName()).isSameAs("recipe_name1");
        assertThat(recipe.getBody().get(1).getName()).isSameAs("recipe_name2");
    }

    @Test
    public void testFetchAllRecipes_When_No_Recipes(){
        when(recipeQueryService.fetchAllRecipes()).thenReturn(new ArrayList<>());
        ResponseEntity<List<RecipeDTO>> response = recipeQueryController.fetchAllRecipes();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

   /* @Test
    public void search(){
        Recipe recipe1 = Recipe
                .builder()
                .name("pizza")
                .type(RecipeType.NON_VEGETARIAN)
                .instructions("spicy")
                .numberOfServings(10)
                .build();

        Recipe recipe2 = Recipe
                .builder()
                .name("pizza")
                .type(RecipeType.VEGETARIAN)
                .instructions("spicy")
                .numberOfServings(10)
                .build();
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        SearchRecipeRequest searchRecipeRequest = new SearchRecipeRequest();
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria nameCriteria = SearchCriteria.builder()
                .filterKey("name")
                .operation(SearchOperation.EQ.name())
                .value("pizza")
                .build();

        SearchCriteria numberOfServingCriteria = SearchCriteria.builder()
                .filterKey("numberOfServings")
                .operation(SearchOperation.EQ.name())
                .value(10)
                .build();

        SearchCriteria instructionsCriteria = SearchCriteria.builder()
                .filterKey("numberOfServings")
                .operation(SearchOperation.EQ.name())
                .value("spicy")
                .build();

        searchCriteriaList.addAll(Arrays.asList(nameCriteria,numberOfServingCriteria,instructionsCriteria));
        searchRecipeRequest.setSearchCriteria(searchCriteriaList);

        when(recipeQueryService.search(searchRecipeRequest, 1, 10))
                .thenReturn(recipes);


        List<Recipe> searchResult = recipeQueryController.search(1, 10, searchRecipeRequest);
        assertThat(searchResult.size()).isSameAs(recipes.size());


    }*/
}
