package com.alma.group8.controller;

import com.alma.group8.interfaces.ProductService;
import com.alma.group8.model.Product;
import com.alma.group8.model.exceptions.AlreadyExistingProductException;
import com.alma.group8.exceptions.FunctionalException;
import com.alma.group8.model.interfaces.ProductsRepository;
import com.alma.group8.util.CommonVariables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

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
    ProductsRepository productsRepository;

    @Autowired
    ProductService productService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = Logger.getLogger(AdminController.class);


    //TODO delete this method, ajouté pour verifier l'accès a /admin
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody public String getProducts(@RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "size", required = false) Integer size) throws FunctionalException {
        LOGGER.info("Receiving a GET request on /products");
        String jsonArrayOfProducts = null;
        Collection<String> products;

        if(page == null || size == null) {
            products = productsRepository.findAll();
        } else {
            products = productsRepository.findPage(page, size);
        }

        try {
            //The results serialized by the object mapper need some refactoring
            jsonArrayOfProducts = OBJECT_MAPPER.writeValueAsString(products).replace("\\", "");
            jsonArrayOfProducts = jsonArrayOfProducts.replace("\"{", "{");
            jsonArrayOfProducts = jsonArrayOfProducts.replace("}\"", "}");
        } catch (JsonProcessingException e) {
            LOGGER.warn("Cannot serialize the result", e);
        }

        return jsonArrayOfProducts;
    }


    /**
     * Delete a product from the database
     * @param id the id of the product to delete
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable String id) throws FunctionalException {
        productsRepository.delete(id);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST, consumes = "application/json")
    public void addProduct(@RequestBody String productAsString) throws FunctionalException {
        try {
            OBJECT_MAPPER.readValue(productAsString, Product.class);
        } catch (IOException e) {
            LOGGER.warn("Incorrect product received", e);
            throw new FunctionalException(e);
        }
        try {
            productsRepository.store(productAsString);
        }catch (AlreadyExistingProductException e){
            LOGGER.warn("Already exists", e);
            //TODO renvoyer des codes erreurs
            throw new FunctionalException(e);
        }
    }

}
