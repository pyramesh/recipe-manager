package com.abnamro.codechallenge.mappers;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.dto.request.CreateRecipeRequest;
import com.abnamro.codechallenge.dto.request.UpdateRecipeRequest;
import com.abnamro.codechallenge.dto.response.RecipeDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RecipeMapper {

    @Mapping(ignore = true, target = "ingredients", source = "ingredients")
    Recipe map(CreateRecipeRequest createRecipeRequest);

    @Mapping(ignore = true, target = "ingredients", source = "ingredients")
    void map(@MappingTarget Recipe recipe, UpdateRecipeRequest updateRecipeRequest);


    RecipeDTO map(Recipe recipe);

    @Condition
    default boolean isNonZero(int value) {
        return value != 0;
    }
}
