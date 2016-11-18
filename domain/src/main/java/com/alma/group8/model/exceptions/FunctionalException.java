package com.alma.group8.model.exceptions;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FunctionalException that = (FunctionalException) o;

        return new EqualsBuilder()
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(message)
                .toHashCode();
    }
}
