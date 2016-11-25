package com.alma.group8.model;

import com.alma.group8.exceptions.FunctionalException;
import com.alma.group8.interfaces.ProductService;
import com.alma.group8.model.exceptions.NotEnoughProductsException;
import com.alma.group8.model.service.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Thibault on 24/11/2016.
 * Test for the service impl class
 */
public class ServiceImplTest {

    private ProductService productService;
    private Product product;
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImplTest.class);

    @Before
    public void setUp() {
        LOGGER.warn("Salut");
        product = new Product();
        product.setId(UUID.randomUUID());
        product.setQuantity(5);
        product.setName("name");
        product.setDescription("description");
        product.setProductType(ProductType.BOIS);
        product.setPrice(2.2);
        productService = new ProductServiceImpl();
    }

    @Test
    public void testReduceQuantity() throws Throwable {
        String productAsString = productService.decreaseQuantity(OBJECT_MAPPER.writeValueAsString(product), 3);
        Assert.assertEquals(2, OBJECT_MAPPER.readValue(productAsString, Product.class).getQuantity());
    }

    @Test(expected = NotEnoughProductsException.class)
    public void testReduceQuantityException() throws Throwable {
        productService.decreaseQuantity(OBJECT_MAPPER.writeValueAsString(product), 10);
    }

    @Test
    public void testIncreaseQuantity() throws IOException, FunctionalException {
        String productAsString = productService.increaseQuantity(OBJECT_MAPPER.writeValueAsString(product), 3);
        Assert.assertEquals(8, OBJECT_MAPPER.readValue(productAsString, Product.class).getQuantity());
    }
}
