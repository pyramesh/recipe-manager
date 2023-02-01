package com.abnamro.codechallenge.services.ingredient;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.exceptions.IngredientNotFoundException;
import com.abnamro.codechallenge.repositories.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientQueryServiceImpl implements IngredientQueryService {

    private final IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> fetchAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient fetchIngredientById(final Integer id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found for id :"+id));
    }

    @Override
    public Ingredient fetchIngredientByName(final String name) {
        return ingredientRepository.findByName(name);
    }

    @Override
    public Set<Ingredient> fetchAllIngredientByIds(final List<Integer> ids) {
        return ingredientRepository.findAllById(ids).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
