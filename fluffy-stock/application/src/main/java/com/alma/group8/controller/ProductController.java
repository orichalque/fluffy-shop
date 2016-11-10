package com.alma.group8.controller;

import com.alma.group8.dto.ProductDTO;
import com.alma.group8.interfaces.ProductsRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Thibault on 10/11/2016.
 * Define the rest controller
 */
@RestController
public class ProductController {

    private static final Gson GSON_MAPPER = new Gson();

    @Autowired
    ProductsRepository productsRepository;

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public @ResponseBody String getProductById(@PathVariable String id) {

        ProductDTO productDTO = productsRepository.find(id);
        return GSON_MAPPER.toJson(productDTO);
    }
}
