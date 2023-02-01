package com.abnamro.codechallenge.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.abnamro.codechallenge.domains.Recipe;

@Repository
public interface RecipeRepository  extends JpaRepository<Recipe, Integer> , JpaSpecificationExecutor<Recipe> {

    Optional<Recipe> findByName(String name);

}
