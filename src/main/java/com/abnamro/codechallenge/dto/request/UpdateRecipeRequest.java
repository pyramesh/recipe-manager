package com.abnamro.codechallenge.dto.request;

import java.util.Set;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRecipeRequest {
    private String name;
    private String instructions;
    private Boolean isVegetarian;
    private int numberOfServings;
    private Set<Integer> ingredients;
}
