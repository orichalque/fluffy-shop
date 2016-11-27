package com.alma.group8.infrastructure.factories;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.domain.model.Role;
import com.alma.group8.domain.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Thibault on 27/11/2016.
 * Factory used to serialize and deserialize Users
 */
public class UserFactoryImpl implements FunctionalFactory<User> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public User deserialize(String metierAsJson) throws FunctionalException {
        try {
            return OBJECT_MAPPER.readValue(metierAsJson, User.class);
        } catch (IOException e) {
            throw new FunctionalException(e);
        }
    }

    @Override
    public String serialize(User object) throws FunctionalException {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FunctionalException(e);
        }
    }
}
