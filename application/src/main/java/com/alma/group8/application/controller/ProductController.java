package com.alma.group8.application.controller;

import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.api.interfaces.ProductService;
import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.ProductsRepository;
import com.alma.group8.application.util.CommonVariables;
import com.alma.group8.domain.exceptions.NotEnoughProductsException;
import com.alma.group8.domain.exceptions.ProductNotFoundException;
import com.alma.group8.domain.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

/**
 * Define the rest controller defining methods that can be called by clients
 */
@CrossOrigin
@RestController
@RequestMapping(value = CommonVariables.ROOT_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController {

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private FunctionalFactory<Product> functionalFactory;


    /**
     * Get the product with a paginated result
     * @param page the page to see
     * @param size the size of the page
     * @return a json containing the products from the page
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody public String getProducts(@RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "size", required = false) Integer size) {
        LOGGER.info("Receiving a GET request on /products");
        String jsonArrayOfProducts = null;
        Collection<String> products;

        if(page == null || size == null) {
            products = productsRepository.findAll();
        } else {
            products = productsRepository.findPage(page, size);
        }

        try {
            //FIXME Use the functionalFactory
            //The results serialized by the object mapper need some refactoring
            jsonArrayOfProducts = new ObjectMapper().writeValueAsString(products).replace("\\", "");
            jsonArrayOfProducts = jsonArrayOfProducts.replace("\"{", "{");
            jsonArrayOfProducts = jsonArrayOfProducts.replace("}\"", "}");
        } catch (JsonProcessingException e) {
            LOGGER.warn("Cannot serialize the result", e);
        }

        return jsonArrayOfProducts;
    }

    /**
     * Get a product using its name
     * @param id the product id
     * @return a Gson containing the product with the corresponding name
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    @ResponseBody public String getProductById(@PathVariable String id) throws ProductNotFoundException {
        LOGGER.info("Receiving a GET request on a single product");
        try {
            return productsRepository.find(id);
        } catch (FunctionalException e) {
            throw new ProductNotFoundException(e);
        }
    }

    /**
     * Order a product using its name
     * @param id the product id
     * @param quantity the quantity to order
     * @return a Gson containing the product with the corresponding name
     * @throws com.alma.group8.domain.exceptions.NotEnoughProductsException
     */
    @RequestMapping(value = "/product/{id}/order/{quantity}", method = RequestMethod.POST)
    @ResponseBody public String orderProductById(@PathVariable String id ,@PathVariable int quantity) throws FunctionalException {
        LOGGER.info("Receiving a POST request to order products");
        String productAsString = null;
        try {
            productAsString = productsRepository.find(id);
        } catch (FunctionalException e) {
            throw new ProductNotFoundException(e);
        }

        try {
            productAsString = productService.decreaseQuantity(productAsString, quantity);
        } catch (FunctionalException e) {
            throw new NotEnoughProductsException(e);
        }

        try {
            productsRepository.updateProduct(productAsString);
        } catch (FunctionalException e) {
            throw new ProductNotFoundException(e);
        }
        return productAsString;
    }
}
