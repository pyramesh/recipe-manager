package com.abnamro.codechallenge.controllers.recipe;

import javax.validation.Valid;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.request.CreateRecipeRequest;
import com.abnamro.codechallenge.dto.request.UpdateRecipeRequest;
import com.abnamro.codechallenge.dto.response.RecipeDTO;
import com.abnamro.codechallenge.mappers.RecipeMapper;
import com.abnamro.codechallenge.services.recipe.RecipeCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipe", description = "Recipe controller for write operations.")
public class RecipeCommandController {

    private final RecipeCommandService recipeService;
    private final RecipeMapper recipeMapper = Mappers.getMapper(RecipeMapper.class);

    @Operation(summary = "Create a new recipe", description = "", tags = { "recipe" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Recipe was created."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    })
    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody CreateRecipeRequest request) {
        Recipe recipe = recipeService.createRecipe(request);
        return new ResponseEntity(recipeMapper.map(recipe), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing recipe", description = "", tags = { "recipe" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The recipe was updated and is present with the new values" +
                    " in the response body."),
            @ApiResponse(responseCode = "400", description = "Bad request. The id provided do not exists."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable(name = "id") Integer id, @RequestBody  @Valid UpdateRecipeRequest updateRecipeRequest) {
        Recipe recipe = recipeService.updateRecipe(id, updateRecipeRequest);
        return ResponseEntity.ok(recipeMapper.map(recipe));
    }


    @Operation(summary = "Deletes a recipe", description = "", tags = { "recipe" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The recipe was successfully deleted."),
            @ApiResponse(responseCode = "400", description = "Bad request, the id provided is invalid."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipeById(@PathVariable(name = "id") Integer id) {
        recipeService.deleteRecipeById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletes all recipe", description = "", tags = { "recipe" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All recipes were successfully deleted."),
            @ApiResponse(responseCode = "400", description = "Bad request, the id provided is invalid."),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    })
    @DeleteMapping
    public ResponseEntity<?> deleteAllRecipes() {
        recipeService.deleteAllRecipes();
        return ResponseEntity.ok().build();
    }



}
