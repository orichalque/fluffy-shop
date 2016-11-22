package com.alma.group8.handlers;

import com.alma.group8.model.Error;
import com.alma.group8.model.exceptions.AlreadyExistingProductException;
import com.alma.group8.model.exceptions.FunctionalException;
import com.alma.group8.model.exceptions.NotEnoughProductsException;
import com.alma.group8.model.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandling.class);

    /**
     * Generate a custom error message when a {@link FunctionalException} is raised
     * @param e the {@link FunctionalException}
     * @return the corresponding {@link Error}
     */
    @ExceptionHandler(FunctionalException.class)
    public String handleFunctionalException(FunctionalException e, HttpServletResponse response) {
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());

        String errorAsString = null;

        try {
            errorAsString = OBJECT_MAPPER.writeValueAsString(error);
        } catch (JsonProcessingException e1) {
            LOGGER.warn("An error occurred due to a functional error", e1);
        }
        response.setStatus(error.getCode());
        return errorAsString;
    }

    /**
     * Generate a custom error message when a {@link AlreadyExistingProductException} is raised
     * @param e the {@link AlreadyExistingProductException}
     * @return the corresponding {@link Error}
     */
    @ExceptionHandler(AlreadyExistingProductException.class)
    public String handleAlreadyExistingProductException(AlreadyExistingProductException e, HttpServletResponse response) {
        Error error = new Error();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());

        String errorAsString = null;

        try {
            errorAsString = OBJECT_MAPPER.writeValueAsString(error);
        } catch (JsonProcessingException e1) {
            LOGGER.warn("An error occurred due product already present", e1);
        }
        response.setStatus(error.getCode());
        return errorAsString;
    }


    /**
     * Generate a custom error message when a {@link ProductNotFoundException} is raised
     * @param e the {@link FunctionalException}
     * @return the corresponding {@link Error}
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(ProductNotFoundException e, HttpServletResponse response) {
        Error error = new Error();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());

        String errorAsString = null;

        try {
            errorAsString = OBJECT_MAPPER.writeValueAsString(error);
        } catch (JsonProcessingException e1) {
            LOGGER.warn("An error occurred due to an absent product", e1);
        }

        response.setStatus(error.getCode());
        return errorAsString;
    }

    /**
     * Generate a custom error message when a {@link FunctionalException} is raised
     * @param e the {@link FunctionalException}
     * @return the corresponding {@link Error}
     */
    @ExceptionHandler(NotEnoughProductsException.class)
    public String handleNotEnoughProductsException(NotEnoughProductsException e, HttpServletResponse response) {
        Error error = new Error();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());

        String errorAsString = null;

        try {
            errorAsString = OBJECT_MAPPER.writeValueAsString(error);
        } catch (JsonProcessingException e1) {
            LOGGER.warn("An error occurred due to a lack of product in the stocks", e1);
        }
        response.setStatus(error.getCode());
        return errorAsString;
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException e, HttpServletResponse response) {
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());

        String errorAsString = null;

        try {
            errorAsString = OBJECT_MAPPER.writeValueAsString(error);
        } catch (JsonProcessingException e1) {
            LOGGER.warn("An error occurred while parsing a file or a product", e1);
        }
        response.setStatus(error.getCode());
        return errorAsString;
    }

    /**
     * Generate a custom error when a {@link RuntimeException} is raised
     * @param e the {@link RuntimeException}
     * @return the corresponding {@link Error}
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleTechnicalException(RuntimeException e, HttpServletResponse response) {
        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(e.getMessage());

        String errorAsString = null;

        try {
            errorAsString = OBJECT_MAPPER.writeValueAsString(error);
        } catch (JsonProcessingException e1) {
            LOGGER.warn("A technical error occurred", e1);
        }
        response.setStatus(error.getCode());
        return errorAsString;
    }

}
