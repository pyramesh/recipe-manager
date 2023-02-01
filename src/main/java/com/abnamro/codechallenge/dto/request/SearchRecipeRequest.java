package com.abnamro.codechallenge.dto.request;

import java.util.List;

import com.abnamro.codechallenge.search.SearchCriteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRecipeRequest {
    private List<SearchCriteria> searchCriteria;
    private String dataOption;
}
