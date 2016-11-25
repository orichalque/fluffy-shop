package com.alma.group8.model.exceptions;

import com.alma.group8.exceptions.FunctionalException;

/**
 * Exception throwed when trying to withdraw more item in the database that there is
 */
public class NotEnoughProductsException extends FunctionalException {

    /**
     * Create a new Exception from a generic one
     * @param e the {@link Exception}
     */
    public NotEnoughProductsException(Exception e) {
        super(e);
    }

    /**
     * Create a new Exception from a custom message
     * @param message the message to set
     */
    public NotEnoughProductsException(String message) {
        super(message);
    }
}
