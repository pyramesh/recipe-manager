package com.abnamro.codechallenge.mappers;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import com.abnamro.codechallenge.domains.Ingredient;
import com.abnamro.codechallenge.dto.request.CreateIngredientRequest;
import com.abnamro.codechallenge.dto.request.UpdateIngredientRequest;
import com.abnamro.codechallenge.dto.response.IngredientDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IngredientMapper {

    Ingredient map(CreateIngredientRequest createIngredientRequest);

    IngredientDTO mapToDto(Ingredient ingredient);

    void map(@MappingTarget Ingredient ingredient, UpdateIngredientRequest updateIngredientRequest);
}
