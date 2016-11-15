package model.service;

import com.alma.group8.interfaces.ProductService;
import model.exceptions.NotEnoughProductsException;

/**
 * Created by Thibault on 15/11/16.
 */
public class ProductServiceImpl implements ProductService {

    @Override
    public void decreaseQuantity(Object product) throws NotEnoughProductsException {
        //TODO
    }

    @Override
    public void increaseQuantity(Object product) {
        //TODO
    }
}
