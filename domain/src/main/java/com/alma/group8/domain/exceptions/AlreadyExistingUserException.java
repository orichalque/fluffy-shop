package com.alma.group8.domain.exceptions;

import com.alma.group8.api.exceptions.FunctionalException;

/**
 * Created by Thibault on 27/11/2016.
 * Throwed when an user already exists and we want to add it
 */
public class AlreadyExistingUserException extends FunctionalException{

    /**
     * Constructor from {@link Exception}
     * @param e the {@link Exception}
     */
    public AlreadyExistingUserException(Exception e) {
        super(e);
    }

    /**
     * Contructor from a message
     * @param message the message
     */
    public AlreadyExistingUserException(String message) {
        super(message);
    }
}
