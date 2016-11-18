package com.alma.group8.model.service;

import com.alma.group8.interfaces.ProductService;
import com.alma.group8.model.Product;
import com.alma.group8.model.exceptions.NotEnoughProductsException;

/**
 * Created by Thibault on 15/11/16.
 */
public class ProductServiceImpl implements ProductService<Product> {


    @Override
    public void decreaseQuantity(Product product, int quantity) throws NotEnoughProductsException {

    }

    @Override
    public void increaseQuantity(Product product) {

    }
}
