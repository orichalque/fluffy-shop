package com.alma.group8.api.exceptions;


/**
 * Created by Thibault on 18/11/2016.
 * Define a generic functional exception that can be throwed when functional error occurred
 */
public class FunctionalException extends Exception {

    private final String message;

    /**
     * Constructor taking an {@link Exception} as a parameter to extract its message
     * @param e an {@link Exception}
     */
    public FunctionalException(Exception e) {
        message = e.getMessage();
    }

    /**
     * Construct a functional exception with a custom message
     * @param message the message to set
     */
    public FunctionalException(String message) {
        this.message = message;
    }

    /**
     * @return current message
     */
    @Override
    public String getMessage() {
        return message;
    }

}
