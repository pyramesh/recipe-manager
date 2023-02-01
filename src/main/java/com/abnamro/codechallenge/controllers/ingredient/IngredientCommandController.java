package com.abnamro.codechallenge.controllers.ingredient;

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

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.request.CreateIngredientRequest;
import com.abnamro.codechallenge.dto.request.UpdateIngredientRequest;
import com.abnamro.codechallenge.dto.response.IngredientDTO;
import com.abnamro.codechallenge.mappers.IngredientMapper;
import com.abnamro.codechallenge.services.ingredient.IngredientCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/ingredients")
@RequiredArgsConstructor
@Slf4j
public class IngredientCommandController {

    private final IngredientCommandService ingredientCommandService;

    IngredientMapper ingredientMapper = Mappers.getMapper(IngredientMapper.class);

    @Operation(summary = "Create a new ingredient", description = "", tags = { "ingredient" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingredient created",
                    content = @Content(schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Ingredient already exists") })
    @PostMapping
    public ResponseEntity createIngredient(@RequestBody @Valid CreateIngredientRequest request) {
        log.info("start IngredientCommandController :: createIngredient");
        Ingredient ingredient = ingredientCommandService.createIngredient(request);
        log.info("end IngredientCommandController :: createIngredient");
        return new ResponseEntity(ingredientMapper.mapToDto(ingredient), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing ingredient by id", description = "", tags = { "ingredient" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID provided"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception") })
    @PutMapping("/{id}")
    public IngredientDTO updateIngredient(@PathVariable(name = "id") Integer id, @RequestBody UpdateIngredientRequest updateIngredientRequest) {
        log.info("start IngredientCommandController :: updateIngredient for id = "+id);
        Ingredient ingredient = ingredientCommandService.updateIngredient(id, updateIngredientRequest);
        log.info("end IngredientCommandController :: updateIngredient ");
        return ingredientMapper.mapToDto(ingredient);

    }


    @Operation(summary = "Deletes a ingredient by id", description = "", tags = { "ingredient" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found") })
    @DeleteMapping("/{id}")
    public void deleteIngredientById(@PathVariable(name = "id") Integer id) {
        log.info("start IngredientCommandController :: deleteIngredientById for id = "+id);
        ingredientCommandService.deleteIngredientById(id);
        log.info("end IngredientCommandController :: deleteIngredientById");
    }

    @Operation(summary = "Deletes all ingredient", description = "", tags = { "ingredient" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found") })
    @DeleteMapping
    public void deleteAllIngredients() {
        log.info("start IngredientCommandController :: deleteAllIngredients");
        ingredientCommandService.deleteAllIngredients();
        log.info("end IngredientCommandController :: deleteAllIngredients");
    }

}
