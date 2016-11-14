package com.alma.group8.controller;

import com.alma.group8.dto.ProductDTO;
import com.alma.group8.interfaces.ProductsRepository;
import com.google.gson.Gson;
import model.exceptions.NotEnoughProductsException;
import model.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Define the rest controller
 */
@RestController
public class ProductController {

    private static final Gson GSON_MAPPER = new Gson();

    @Autowired
    ProductsRepository productsRepository;

    /**
     * Get all the products
     * @return a Gson containing all the products, or an empty Gson
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody String getAllProducts() {

        Collection<ProductDTO> products = productsRepository.findAll();
        return GSON_MAPPER.toJson(products);
    }

    /**
     * Get all the products of a type
     * @param type the products type
     * @return a Gson containing all the products, or an empty Gson
     */
    @RequestMapping(value = "/products/{type}", method = RequestMethod.GET)
    public @ResponseBody String getAllProductsByType(@PathVariable String type) {

        Collection<ProductDTO> products = productsRepository.findProductsByType(type);
        return GSON_MAPPER.toJson(products);
    }

    /**
     * Get a product using its name
     * @param id the product id
     * @return a Gson containing the product with the corresponding name
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public @ResponseBody String getProductById(@PathVariable String id) {

        ProductDTO productDTO = productsRepository.find(id);
        return GSON_MAPPER.toJson(productDTO);
    }

    /**
     * Order a product using its name
     * @param id the product id
     * @param quantity the quantity to order
     * @return a Gson containing the product with the corresponding name
     */
    @RequestMapping(value = "/product/{id}/order/{quantity}", method = RequestMethod.POST)
    public @ResponseBody String orderProductById(@PathVariable String id ,@PathVariable int quantity) throws NotEnoughProductsException {

        ProductDTO productDTO = productsRepository.orderProduct(id, quantity);
        //FIXME : DTO -> Data -> Decrease Quantity -> DTO -> update bdd
        return GSON_MAPPER.toJson(productDTO);
    }
}
