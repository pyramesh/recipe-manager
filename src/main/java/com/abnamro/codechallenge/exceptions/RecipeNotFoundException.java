
package com.abnamro.codechallenge.exceptions;

/**
 * Exception class for recipe not found exception
 */
public class RecipeNotFoundException extends RuntimeException {
    /**
     * constructor
     *
     * @param message
     *            the message
     */
    public RecipeNotFoundException(String message) {
        super(message);
    }
}
