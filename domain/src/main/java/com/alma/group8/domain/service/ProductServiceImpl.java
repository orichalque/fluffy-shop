package com.alma.group8.domain.service;

import com.alma.group8.api.interfaces.ProductService;
import com.alma.group8.domain.model.Product;
import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.domain.exceptions.InvalidProductException;
import com.alma.group8.domain.exceptions.NotEnoughProductsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Thibault on 15/11/16.
 * {@link ProductService} implementation
 */
public class ProductServiceImpl implements ProductService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Override
    public String decreaseQuantity(String productAsString, int quantity) throws FunctionalException {
        Product product;
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
    public String increaseQuantity(String productAsString, int quantity) throws FunctionalException {
        Product product;
        String serializedProduct;

        try {
            product = OBJECT_MAPPER.readValue(productAsString, Product.class);
            int currentQuantity = product.getQuantity();
            product.setQuantity(currentQuantity+quantity);
            serializedProduct = OBJECT_MAPPER.writeValueAsString(product);
        } catch (IOException e) {
            throw new FunctionalException(e);
        }

        return serializedProduct;
    }

    @Override
    public void validate(String productAsString) throws FunctionalException {
        try {
            Product product = OBJECT_MAPPER.readValue(productAsString, Product.class);
            if (product.getQuantity() < 0 ) {
                throw new InvalidProductException("Product not valid");
            }
        } catch (IOException e) {
            throw new InvalidProductException(e);
        }
    }
}
