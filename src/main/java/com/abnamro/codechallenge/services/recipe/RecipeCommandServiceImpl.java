package com.abnamro.codechallenge.services.recipe;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.request.CreateRecipeRequest;
import com.abnamro.codechallenge.dto.request.UpdateRecipeRequest;
import com.abnamro.codechallenge.exceptions.RecipeNotFoundException;
import com.abnamro.codechallenge.mappers.RecipeMapper;
import com.abnamro.codechallenge.repositories.RecipeRepository;
import com.abnamro.codechallenge.services.ingredient.IngredientQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final RecipeMapper recipeMapper = Mappers.getMapper(RecipeMapper.class);

    private final RecipeRepository recipeRepository;

    private final IngredientQueryService ingredientQueryService;

    @Override
    public void deleteRecipeById(Integer id) {
        if(!recipeRepository.existsById(id)) {
         throw new RecipeNotFoundException("Recipe is not found for id ="+id);
        }
        recipeRepository.deleteById(id);
    }

    @Override
    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }

    @Override
    public Recipe updateRecipe(Integer recipeId, UpdateRecipeRequest updateRecipeRequest) {
        log.info("start RecipeCommandServiceImpl:: updateRecipe for id ", recipeId);
        Recipe recipe = null;
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            throw  new RecipeNotFoundException("Recipe is not found for id "+recipeId);
        }else {
            Recipe recipeDb = recipeOptional.get();
            recipeMapper.map(recipeDb, updateRecipeRequest);
            if(updateRecipeRequest.getIngredients() != null) {
                Set<Ingredient> ingredients = getIngredients(updateRecipeRequest.getIngredients());
                if (Optional.ofNullable(ingredients).isPresent()) recipeDb.setIngredients(ingredients);
            }
            recipe= recipeRepository.save(recipeDb);
        }

        return recipe;
    }

    @Override
    public Recipe createRecipe(CreateRecipeRequest createRecipeRequest) {
        Recipe recipe = recipeMapper.map(createRecipeRequest);
        if(createRecipeRequest.getIngredients() != null) {
            Set<Ingredient> ingredients = getIngredients(createRecipeRequest.getIngredients());
            recipe.setIngredients(ingredients);
        }
        return recipeRepository.save(recipe);
    }

    private Set<Ingredient> getIngredients(final Set<Integer> ingredientIds) {
        Set<Ingredient> ingredients = ingredientIds.stream()
                .map(ingredientQueryService::fetchIngredientById)
                .collect(Collectors.toSet());
        return ingredients;
    }
}
