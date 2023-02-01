package com.abnamro.codechallenge.services.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.abnamro.codechallenge.domains.Recipe;
import com.abnamro.codechallenge.exceptions.RecipeNotFoundException;
import com.abnamro.codechallenge.repositories.RecipeRepository;
import com.abnamro.codechallenge.search.RecipeSearchRequest;
import com.abnamro.codechallenge.search.RecipeSpecificationBuilder;
import com.abnamro.codechallenge.search.SearchCriteria;
import com.abnamro.codechallenge.search.SearchCriteriaRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeQueryServiceImpl implements RecipeQueryService{

    private final RecipeRepository recipeRepository;

    @Override
    public List<Recipe> fetchAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe fetchRecipeById(Integer id) {
        return recipeRepository.findById(id)
                .orElseThrow(()-> new RecipeNotFoundException("Recipe not found for the id "+id));
    }

    @Override
    public List<Recipe> search(final RecipeSearchRequest recipeSearchRequest, int page, int size) {
        List<SearchCriteria> searchCriterionRequests = new ArrayList<>();
        RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder(searchCriterionRequests);
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("name")
                .ascending());

        Specification<Recipe> recipeSpecification = createRecipeSpecification(recipeSearchRequest, builder);
        Page<Recipe> filteredRecipes = recipeRepository.findAll(recipeSpecification, pageRequest);

        return filteredRecipes.toList().stream()
                .collect(Collectors.toList());

    }
    private Specification<Recipe> createRecipeSpecification(RecipeSearchRequest recipeSearchRequest,
                                                            RecipeSpecificationBuilder builder) {
        List<SearchCriteriaRequest> searchCriteriaRequests = recipeSearchRequest.getSearchCriteriaRequests();

        if (Optional.ofNullable(searchCriteriaRequests).isPresent()) {
            List<SearchCriteria> searchCriteriaList = searchCriteriaRequests.stream()
                    .map(SearchCriteria::new)
                    .collect(Collectors.toList());

            if (!searchCriteriaList.isEmpty()) searchCriteriaList.forEach(criteria -> {
                criteria.setDataOption(recipeSearchRequest.getDataOption());
                builder.with(criteria);
            });
        }

        return builder.build().orElseThrow(() -> new RecipeNotFoundException("Recipe is not found"));
    }
}
