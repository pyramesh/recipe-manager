package com.abnamro.codechallenge.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.abnamro.codechallenge.domains.Recipe;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecipeRepository recipeRepository;

    @Before
    public void init(){
        recipeRepository.deleteAll();
    }
    @Test
    public void testSave_Recipe() {
        Recipe recipe = Recipe
                .builder()
                .name("recipe_name")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions")
                .build();
        Recipe persistedRecipe = recipeRepository.save(recipe);

        assertNotNull(persistedRecipe);
        assertThat(recipe.getId()).isSameAs(persistedRecipe.getId());
        assertThat(recipe.getName()).isSameAs(persistedRecipe.getName());
    }

    @Test
    public void testFindById() {
        Recipe recipe = Recipe
                .builder()
                .name("vegetable curry")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions")
                .build();
        Recipe persistedRecipe = recipeRepository.save(recipe);
        Optional<Recipe> recipeResponse = recipeRepository.findById(persistedRecipe.getId());
        assertThat(recipeResponse.get().getId()).isSameAs(recipe.getId());
        assertThat(recipeResponse.get().getName()).isSameAs(recipe.getName());
    }

    @Test
    public void testFindAll() {
        Recipe recipe1 = Recipe
                .builder()
                .name("curry")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions1")
                .build();

        Recipe recipe2 = Recipe
                .builder()
                .name("vegetable curry2")
                .isVegetarian(Boolean.TRUE)
                .instructions("instructions2")
                .build();

        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        Recipe persistedRecipe1 = recipeRepository.save(recipe1);
        Recipe persistedRecipe2 = recipeRepository.save(recipe2);
        List<Recipe> fetchResponse = recipeRepository.findAllById(Arrays.asList(persistedRecipe1.getId(), persistedRecipe2.getId()));
        assertThat(fetchResponse.size()).isSameAs(recipes.size());
    }

}
