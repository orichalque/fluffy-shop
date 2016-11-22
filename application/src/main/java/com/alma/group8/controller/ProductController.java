package com.alma.group8.controller;

import com.alma.group8.interfaces.ProductService;
import com.alma.group8.model.Product;
import com.alma.group8.model.exceptions.FunctionalException;
import com.alma.group8.model.interfaces.ProductsRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    ProductService<FunctionalException> productService;

    /**
     * Get the product with a paginated result
     * @param page the page to see
     * @param size the size of the page
     * @return a json containing the products from the page
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody public String getProducts(@RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "size", required = false) Integer size) throws FunctionalException, JsonProcessingException {

        String jsonArrayOfProducts = null;
        Collection<String> products;

        if(page == null || size == null) {
            products = productsRepository.findAll();
        } else {
            products = productsRepository.findPage(page, size);
        }

        jsonArrayOfProducts = OBJECT_MAPPER.writeValueAsString(products);

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
    @ResponseBody public String orderProductById(@PathVariable String id ,@PathVariable int quantity) throws FunctionalException, IOException {
        String productAsString = productsRepository.find(id);

        Product product = OBJECT_MAPPER.readValue(productAsString, Product.class);

        productAsString = productService.decreaseQuantity(productAsString, quantity);

        productsRepository.updateProduct(productAsString);
        return productAsString;
    }
}
