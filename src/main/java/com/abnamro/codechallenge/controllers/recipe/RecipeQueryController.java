package com.abnamro.codechallenge.controllers.recipe;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.response.RecipeDTO;
import com.abnamro.codechallenge.mappers.RecipeMapper;
import com.abnamro.codechallenge.search.RecipeSearchRequest;
import com.abnamro.codechallenge.services.recipe.RecipeQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipe", description = "Recipe controller for read operations.")
@Slf4j
public class RecipeQueryController {

    private final RecipeQueryService recipeQueryService;
    private final RecipeMapper recipeMapper = Mappers.getMapper(RecipeMapper.class);
    @Operation(summary = "Get all recipes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all recipes.",
                    content =@Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))) })
    @GetMapping
    public ResponseEntity<List<RecipeDTO>> fetchAllRecipes() {
        log.info("start RecipeQueryController:: fetchAllRecipes");
        List<Recipe> recipes = recipeQueryService.fetchAllRecipes();
        if(recipes.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        List<RecipeDTO> recipeDTOS = recipes.stream()
                .filter(Objects::nonNull)
                .map(recipe -> recipeMapper.map(recipe))
                .collect(Collectors.toList());
        log.info("end RecipeQueryController:: fetchAllRecipes");
        return new ResponseEntity(recipeDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Get a recipe by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the recipe", content = { @Content(mediaType = "application/json",schema = @Schema(implementation = Recipe.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id provided",  content = @Content),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> fetchRecipeById(@PathVariable(name = "id") Integer id) {
        log.info("start RecipeQueryController:: fetchRecipeById for id ", id);
        Recipe recipe = recipeQueryService.fetchRecipeById(id);

        log.info("end RecipeQueryController:: fetchRecipeById");
        return new ResponseEntity(recipeMapper.map(recipe), HttpStatus.OK);

    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search was successful"),
            @ApiResponse(responseCode = "404", description = "Different error messages related to criteria and recipe")

    })
    @PostMapping("/search")
    public List<RecipeDTO> search(@RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "10") int size,
                           @RequestBody @Valid RecipeSearchRequest recipeSearchRequest){
        return recipeQueryService.search(recipeSearchRequest, page, size).stream().filter(Objects::nonNull).map(recipe -> recipeMapper.map(recipe)).collect(Collectors.toList());
    }


}
