package com.alma.group8.application.handlers;

import com.alma.group8.api.exceptions.TechnicalException;
import com.alma.group8.domain.exceptions.*;
import com.alma.group8.domain.model.Error;
import com.alma.group8.api.exceptions.FunctionalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Thibault on 18/11/2016.
 * Define exception handlers methods, used to set specific error message
 * when exception are throwed during the controller's job
 */
@ControllerAdvice
public class ExceptionHandling {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandling.class);

    /**
     * Generate a custom error message when a {@link AlreadyExistingProductException} is raised
     * @param e the {@link AlreadyExistingProductException}
     * @param response the {@link HttpServletResponse}
     * @return the corresponding {@link Error}
     */
    @ExceptionHandler({AlreadyExistingProductException.class, AlreadyExistingUserException.class})
    public String handleAlreadyExistingProductException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.CONFLICT.value());
        return createErrorAsString(HttpStatus.CONFLICT, e.getMessage());
    }

    /**
     * Generate a custom error message when a {@link ProductNotFoundException} is raised
     * @param e the {@link FunctionalException}
     * @param response the {@link HttpServletResponse}
     * @return the corresponding {@link Error}
     */
    @ExceptionHandler({UserNotFoundException.class, NotEnoughProductsException.class})
    public String handleNotFoundException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return createErrorAsString(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /**
     * Generate a custom error message when a {@link ProductNotFoundException} is raised
     * @param e the {@link FunctionalException}
     * @param response the {@link HttpServletResponse}
     * @return the corresponding {@link Error}
     */
    @ExceptionHandler({ProductNotFoundException.class})
    public String handleProductNotFoundException(ProductNotFoundException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return createErrorAsString(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /**
     * Generate a custom error message when an {@link IOException} is throwed
     * @param e tje {@link IOException}
     * @param response the {@link HttpServletResponse}
     * @return the custom {@link Error}
     */
    @ExceptionHandler({IOException.class, FunctionalException.class})
    public String handleIOException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return createErrorAsString(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * Generate a custom error when a {@link RuntimeException} or a {@link TechnicalException} is raised
     * @param e the {@link Exception}
     * @param response the {@link HttpServletResponse}
     * @return the corresponding {@link Error}
     */
    @ExceptionHandler({RuntimeException.class, TechnicalException.class})
    public String handleTechnicalException(Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return createErrorAsString(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * Create an Error String
     * @param httpStatus the HttpStatus
     * @param message the message as a String
     * @return the custom serialized Error
     */
    private String createErrorAsString(HttpStatus httpStatus, String message) {
        LOGGER.warn(String.format("%s error", httpStatus));
        Error error = new Error();
        error.setCode(httpStatus.value());
        error.setMessage(message);

        String errorAsString = null;

        try {
            errorAsString = OBJECT_MAPPER.writeValueAsString(error);
        } catch (JsonProcessingException e1) {
            LOGGER.warn("A technical error occurred", e1);
        }

        return errorAsString;
    }
}