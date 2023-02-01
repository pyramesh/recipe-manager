package com.abnamro.codechallenge.search;

import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeSearchRequest {
    @Valid
    private List<SearchCriteriaRequest> searchCriteriaRequests;
    private String dataOption;
}
