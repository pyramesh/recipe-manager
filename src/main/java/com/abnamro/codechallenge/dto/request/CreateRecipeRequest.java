package com.abnamro.codechallenge.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRecipeRequest {
    private String name;
    private String instructions;
    private Boolean isVegetarian;
    private Integer numberOfServings;
    private Set<Integer> ingredients;
}
