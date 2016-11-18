package com.alma.group8.controller;

import com.alma.group8.interfaces.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Product;
import model.interfaces.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

/**
 * Define the rest controller
 */
@RestController
@RequestMapping("/api")
public class ProductController {

    //FIXME catch exceptions to do a custom answer handler

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ProductService<Product> productService;

    /**
     * Get all the products
     * @return a Gson containing all the products, or an empty Gson
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody String getAllProducts() {

        Collection<String> products = productsRepository.findAll();
        String jsonArrayOfProducts = null;

        try {
             jsonArrayOfProducts = OBJECT_MAPPER.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //FIXME LOGGER
        }

        return jsonArrayOfProducts;
    }

    //TODO : get paginated /products?page=x&size=20

    /**
     * Get a product using its name
     * @param id the product id
     * @return a Gson containing the product with the corresponding name
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public @ResponseBody String getProductById(@PathVariable String id) {

        return productsRepository.find(id);
    }

    /**
     * Order a product using its name
     * @param id the product id
     * @param quantity the quantity to order
     * @return a Gson containing the product with the corresponding name
     */
    @RequestMapping(value = "/product/{id}/order/{quantity}", method = RequestMethod.POST)
    public @ResponseBody String orderProductById(@PathVariable String id ,@PathVariable int quantity) throws Exception {
        String productAsString = productsRepository.find(id);
        Product product = null;

        try {
            product = OBJECT_MAPPER.readValue(productAsString, Product.class);
        } catch (IOException e) {
            e.printStackTrace();
            //FIXME LOG
        }

        productService.decreaseQuantity(product, quantity);
        productAsString = OBJECT_MAPPER.writeValueAsString(product);
        productsRepository.updateProduct(productAsString);
        return productAsString;
    }
}
