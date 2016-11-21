package com.alma.group8.model.service;

import com.alma.group8.interfaces.ProductService;
import com.alma.group8.model.Product;
import com.alma.group8.model.exceptions.FunctionalException;
import com.alma.group8.model.exceptions.NotEnoughProductsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Thibault on 15/11/16.
 * {@link ProductService} implementation
 */
public class ProductServiceImpl implements ProductService<FunctionalException> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Override
    public String decreaseQuantity(String productAsString, int quantity) throws FunctionalException {
        Product product = null;
        String serializedProduct = null;
        try {
            product = OBJECT_MAPPER.readValue(productAsString, Product.class);
            int currentQuantity = product.getQuantity();

            if (quantity > currentQuantity) {
                throw new NotEnoughProductsException("Cannot decrease the quantity");
            } else {
                product.setQuantity(currentQuantity - quantity);
            }

            serializedProduct = OBJECT_MAPPER.writeValueAsString(product);
        } catch (IOException e) {
            LOGGER.warn("Invalid product", e);
        }

        return serializedProduct;
    }

    @Override
    public String increaseQuantity(String productAsString, int quantity) {
        Product product;
        String serializedProduct = null;

        try {
            product = OBJECT_MAPPER.readValue(productAsString, Product.class);
            int currentQuantity = product.getQuantity();
            product.setQuantity(currentQuantity+quantity);
            serializedProduct = OBJECT_MAPPER.writeValueAsString(product);
        } catch (IOException e) {
            LOGGER.warn("Invalid product", e);
        }

        return serializedProduct;
    }
}
