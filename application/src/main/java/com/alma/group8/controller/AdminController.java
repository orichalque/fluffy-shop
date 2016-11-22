package com.alma.group8.controller;

import com.alma.group8.interfaces.ProductService;
import com.alma.group8.model.Product;
import com.alma.group8.model.exceptions.FunctionalException;
import com.alma.group8.model.interfaces.ProductsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Thibault on 18/11/16.
 * Spring controller meant to access to the private functionalities of fluffy-stock
 * Is not meant to be used by outsiders
 */
@CrossOrigin("http://fluffy-stock-presentation.herokuapp.com")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ProductService<FunctionalException> productService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    /**
     * Delete a product from the database
     * @param id the id of the product to delete
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable String id) throws FunctionalException {
        productsRepository.delete(id);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public void addProduct(@RequestBody String productAsString) throws FunctionalException {
        try {
            OBJECT_MAPPER.readValue(productAsString, Product.class);
        } catch (IOException e) {
            LOGGER.warn("Incorrect product received", e);
            throw new FunctionalException(e);
        }

    }

}
