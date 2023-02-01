package com.abnamro.codechallenge.controllers.ingredient;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.services.ingredient.IngredientQueryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/ingredients")
@RequiredArgsConstructor
@Slf4j
public class IngredientQueryController {

    private final IngredientQueryService ingredientQueryService;

    @GetMapping()
    public ResponseEntity<List<Ingredient>> fetchAllIngredients() {
        log.info("start IngredientQueryController :: fetchAllIngredients ");
        List<Ingredient> ingredients = ingredientQueryService.fetchAllIngredients();
        if(ingredients.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("end IngredientQueryController :: fetchAllIngredients ");
        return new ResponseEntity(ingredients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> fetchIngredientById(@PathVariable(name = "id") Integer id) {
        log.info("start IngredientQueryController :: fetchIngredientById for id  ="+id);
        Ingredient ingredient = ingredientQueryService.fetchIngredientById(id);
        log.info("end IngredientQueryController :: fetchIngredientById");
        return new ResponseEntity(ingredient, HttpStatus.OK);
    }

}
