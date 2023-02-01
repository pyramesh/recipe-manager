package com.abnamro.codechallenge.services.ingredient;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.dto.request.CreateIngredientRequest;
import com.abnamro.codechallenge.dto.request.UpdateIngredientRequest;
import com.abnamro.codechallenge.exceptions.IngredientNotFoundException;
import com.abnamro.codechallenge.mappers.IngredientMapper;
import com.abnamro.codechallenge.repositories.IngredientRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IngredientCommandServiceImpl implements IngredientCommandService {

    IngredientMapper ingredientMapper = Mappers.getMapper(IngredientMapper.class);
    private final IngredientRepository ingredientRepository;


    @Override
    public Ingredient createIngredient(final CreateIngredientRequest createIngredientRequest) {

        Ingredient ingredient = ingredientMapper.map(createIngredientRequest);
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient updateIngredient(final Integer ingredientId, final UpdateIngredientRequest updateIngredientRequest) {
        log.info("start IngredientCommandServiceImpl:: updateIngredient for id ", ingredientId);
        if(ingredientRepository.existsById(ingredientId)){
            Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
            ingredientMapper.map(ingredient, updateIngredientRequest);
            log.info("end IngredientCommandServiceImpl:: updateIngredient");
           return ingredientRepository.save(ingredient);
        }else{
            throw new IngredientNotFoundException("Ingredient not found for id :"+ingredientId);
        }

    }

    @Override
    public void deleteIngredientById(final Integer id) {
        if(!ingredientRepository.existsById(id)){
            throw new IngredientNotFoundException("Ingredient is not found for id ="+id);
        }
        ingredientRepository.deleteById(id);
    }

    @Override
    public void deleteAllIngredients() {
        ingredientRepository.deleteAll();
    }

}
