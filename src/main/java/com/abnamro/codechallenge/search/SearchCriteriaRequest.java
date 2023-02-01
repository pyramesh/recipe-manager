package com.abnamro.codechallenge.search;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Valid
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteriaRequest {

    private String filterKey;
    private Object value;
    private String operation;
    private String dataOption;

    public SearchCriteriaRequest(String filterKey, Object value, String operation) {
        this.filterKey = filterKey;
        this.value = value;
        this.operation = operation;
    }

    }
