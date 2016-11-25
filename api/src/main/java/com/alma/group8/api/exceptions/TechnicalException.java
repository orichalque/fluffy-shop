package com.alma.group8.api.exceptions;

/**
 * Created by Thibault on 25/11/16.
 * Technical exception throwed when technical error occur
 */
public class TechnicalException extends Exception {

    private final String message;

    /**
     * Constructor with a custom message
     * @param message the message of the exception
     */
    public TechnicalException(String message) {
        this.message = message;
    }

    /**
     * Constructor with a custom exception. Use the message of the exception for the message of the current technical
     * @param e the Exception
     */
    public TechnicalException(Exception e) {
        this.message = e.getMessage();
    }

    /**
     * Return the current message of the exception
     * @return the current message
     */
    public String getMessage() {
        return message;
    }
}
