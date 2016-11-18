package com.alma.group8.model.exceptions;

/**
 * Exception raised when adding a product already existing in the database
 */
public class AlreadyExistingProductException extends FunctionalException {

    /**
     * Construct a new Functional exception from a generic one
     * @param e the {@link Exception}
     */
    public AlreadyExistingProductException(Exception e) {
        super(e);
    }

    /**
     * Construct a new Functional exception with a custom message
     * @param message the message to set
     */
    public AlreadyExistingProductException(String message) {
        super(message);
    }
}
