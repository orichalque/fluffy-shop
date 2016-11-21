package com.alma.group8.controller;

import com.alma.group8.interfaces.ProductService;
import com.alma.group8.model.Product;
import com.alma.group8.model.exceptions.FunctionalException;
import com.alma.group8.model.interfaces.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Thibault on 18/11/16.
 * Spring controller meant to access to the private functionalities of fluffy-stock
 * Is not meant to be used by outsiders
 */
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ProductService<Product> productService;

    /**
     * Delete a product from the database
     * @param id the id of the product to delete
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable String id) throws FunctionalException {
        productsRepository.delete(id);
    }

}
