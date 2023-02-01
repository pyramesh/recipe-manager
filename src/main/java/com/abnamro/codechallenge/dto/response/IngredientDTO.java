package com.abnamro.codechallenge.dto.response;

import java.time.LocalDateTime;

import com.abnamro.codechallenge.domains.Ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {

    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public IngredientDTO(final Ingredient ingredient) {
        id = ingredient.getId();
        name = ingredient.getName();
        createdAt = ingredient.getCreatedAt();
        updatedAt = ingredient.getUpdatedAt();
    }
}
