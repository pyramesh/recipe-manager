package com.abnamro.codechallenge.exceptions;

/**
 * Exception class for ingredient not found exception
 */
public class IngredientNotFoundException extends RuntimeException {
    /**
     * constructor
     *
     * @param message
     *            the message
     */
    public IngredientNotFoundException(String message) {
        super(message);
    }
}
