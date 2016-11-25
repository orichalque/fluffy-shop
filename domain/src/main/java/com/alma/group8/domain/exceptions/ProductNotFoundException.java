package com.alma.group8.domain.exceptions;

import com.alma.group8.api.exceptions.FunctionalException;

/**
 * Exception raised when trying to update or delete an item not existing in the database
 */
public class ProductNotFoundException extends FunctionalException {

    /**
     * Create a new Exception from a generic one
     * @param e the {@link Exception}
     */
    public ProductNotFoundException(Exception e) {
        super(e);
    }

    /**
     * Create a new Exception with a custom message
     * @param message the message to set
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
