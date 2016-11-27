package com.alma.group8.domain.factories;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.domain.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Dennis on 25/11/16.
 * Factory implementation serializing and deserializing the Products
 */
public class ProductFactoryImpl implements FunctionalFactory<Product> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public Product deserialize(String metierAsJson) throws FunctionalException {
        try {
            return OBJECT_MAPPER.readValue(metierAsJson, Product.class);
        } catch (IOException e) {
            throw new FunctionalException(e);
        }
    }

    @Override
    public String serialize(Product object) throws FunctionalException {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FunctionalException(e);
        }
    }
}
