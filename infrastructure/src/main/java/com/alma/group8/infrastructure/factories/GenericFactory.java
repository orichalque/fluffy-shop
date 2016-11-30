package com.alma.group8.infrastructure.factories;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.FunctionalFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Thibault on 27/11/2016.
 * Generic factory using ObjectMapper
 */
public class GenericFactory<T> implements FunctionalFactory<T> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Class<T> type;

    /**
     * Constructor with a type
     * @param type the type
     */
    public GenericFactory(Class<T> type) {
        this.type = type;
    }

    @Override
    public T deserialize(String metierAsJson) throws FunctionalException {
        try {
            return OBJECT_MAPPER.readValue(metierAsJson, type);
        } catch (IOException e) {
            throw new FunctionalException(e);
        }
    }

    @Override
    public String serialize(T object) throws FunctionalException {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FunctionalException(e);
        }
    }
}
