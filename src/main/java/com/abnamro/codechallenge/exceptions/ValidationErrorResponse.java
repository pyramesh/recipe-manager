package com.abnamro.codechallenge.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponse {
    private final List<Violation> violations = new ArrayList<>();

    public ValidationErrorResponse() {
    }


}
