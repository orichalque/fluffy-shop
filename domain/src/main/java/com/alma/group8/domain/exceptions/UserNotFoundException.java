package com.alma.group8.domain.exceptions;

import com.alma.group8.api.exceptions.FunctionalException;

/**
 * Created by Thibault on 27/11/2016.
 * Throwed when an user is requested but not in the base
 */
public class UserNotFoundException extends FunctionalException {

    /**
     * Constructor from {@link Exception}
     * @param e the {@link Exception}
     */
    public UserNotFoundException(Exception e) {
        super(e);
    }

    /**
     * Constructor from message
     * @param message the message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
