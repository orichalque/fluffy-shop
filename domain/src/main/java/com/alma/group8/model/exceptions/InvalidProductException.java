package com.alma.group8.model.exceptions;

import com.alma.group8.exceptions.FunctionalException;

/**
 * Created by Thibault on 25/11/16.
 * Exception throwed when the product is not valid
 */
public class InvalidProductException extends FunctionalException {
    /**
     * Constructor from an exception
     * @param e the exception
     */
    public InvalidProductException(Exception e) {
        super(e);
    }

    /**
     * Constructor from a custom text
     * @param message the message to set in the InvalidProductException
     */
    public InvalidProductException(String message) {
        super(message);
    }
}
