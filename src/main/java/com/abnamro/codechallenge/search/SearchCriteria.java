package com.abnamro.codechallenge.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria {
    private String filterKey;
    private Object value;
    private String operation;
    private String dataOption;

    public SearchCriteria(SearchCriteriaRequest request) {
        dataOption = request.getDataOption();
        filterKey = request.getFilterKey();
        operation = request.getOperation();
        value = request.getValue();
    }
}
