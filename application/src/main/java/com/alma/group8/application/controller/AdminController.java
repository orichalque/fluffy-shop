package com.alma.group8.application.controller;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.api.interfaces.ProductService;
import com.alma.group8.api.interfaces.ProductsRepository;
import com.alma.group8.application.util.CommonVariables;
import com.alma.group8.domain.exceptions.AlreadyExistingProductException;
import com.alma.group8.domain.model.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Thibault on 18/11/16.
 * Spring controller meant to access to the private functionalities of fluffy-stock
 * Is not meant to be used by outsiders
 */
@CrossOrigin
@RestController
@RequestMapping(value = CommonVariables.ADMIN_URL)
public class AdminController {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private FunctionalFactory<Product> productFactory;

    private static final Logger LOGGER = Logger.getLogger(AdminController.class);

    /**
     * Delete a product from the database
     * @param id the id of the product to delete
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable String id) throws FunctionalException {
        productsRepository.delete(id);
    }

    /**
     * Add a product in the database
     * @param productAsString the product
     * @throws FunctionalException
     */
    @RequestMapping(value = "/product", method = RequestMethod.POST, consumes = "application/json")
    public void addProduct(@RequestBody String productAsString) throws FunctionalException {
        LOGGER.info("Receiving a POST method");

        Product product = productFactory.deserialize(productAsString);
        product.setId(UUID.randomUUID()); //TODO add this method in the Service from the domain
        //TODO validate the product with a method from the domain's service
        productAsString = productFactory.serialize(product);
        try {
            productsRepository.store(productAsString);
        } catch (FunctionalException e) {
            throw new AlreadyExistingProductException(e);
        }
    }

}
