package com.alma.group8.controller;

import com.alma.group8.interfaces.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alma.group8.model.Product;
import com.alma.group8.model.exceptions.FunctionalException;
import com.alma.group8.model.exceptions.NotEnoughProductsException;
import com.alma.group8.model.interfaces.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

/**
 * Define the rest controller defining methods that can be called by clients
 */
@RestController
@RequestMapping("/")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ProductService<Product> productService;

    /**
     * Get all the products
     * @return a json containing all the products, or an empty Gson
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody public String getAllProducts() throws FunctionalException {

        Collection<String> products = productsRepository.findAll();
        String jsonArrayOfProducts = null;

        try {
             jsonArrayOfProducts = OBJECT_MAPPER.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            LOGGER.warn("An error expected while serializing all the data fetched from the database", e);
        }

        return jsonArrayOfProducts;
    }

    /**
     * Get the product with a paginated result
     * @param page the page to see
     * @param size the size of the page
     * @return a json containing the products from the page
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody public String getProductsPaginated(@RequestParam("page") int page, @RequestParam("size") int size) throws FunctionalException {
        Collection<String> products = productsRepository.findPage(page, size);
        String jsonArrayOfProducts = null;

        try {
            jsonArrayOfProducts = OBJECT_MAPPER.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            LOGGER.warn("An error expected while serializing the page fetched from the database", e);
        }

        return jsonArrayOfProducts;
    }

    /**
     * Get a product using its name
     * @param id the product id
     * @return a Gson containing the product with the corresponding name
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    @ResponseBody public String getProductById(@PathVariable String id) throws FunctionalException {
        return productsRepository.find(id);
    }

    /**
     * Order a product using its name
     * @param id the product id
     * @param quantity the quantity to order
     * @return a Gson containing the product with the corresponding name
     * @throws com.alma.group8.model.exceptions.NotEnoughProductsException
     */
    @RequestMapping(value = "/product/{id}/order/{quantity}", method = RequestMethod.POST)
    @ResponseBody public String orderProductById(@PathVariable String id ,@PathVariable int quantity) throws FunctionalException {
        String productAsString = productsRepository.find(id);
        Product product = null;

        try {
            product = OBJECT_MAPPER.readValue(productAsString, Product.class);
        } catch (IOException e) {
            LOGGER.warn("An error expected while requesting the product in the database", e);
        }

        try {
            productService.decreaseQuantity(product, quantity);
        } catch (Exception e) {
            throw new NotEnoughProductsException(e);
        }

        try {
            productAsString = OBJECT_MAPPER.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            LOGGER.warn("An error expected while serializing the page fetched from the database", e);
        }

        productsRepository.updateProduct(productAsString);
        return productAsString;
    }
}
