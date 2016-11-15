package com.alma.group8.interfaces;


/**
 * Created by Thibault on 15/11/16.
 * Define the Service implemented in the Domain Layer
 */
public interface ProductService<T>  {

    /**
     * Reduce the quantity of a Product
     * @param product the product
     * @param quantity
     * @throws Exception if the quantity goes below 0
     */
    void decreaseQuantity(T product, int quantity) throws Exception;

    /**
     * Increase the quantity of a Product
     * @param product the product to increase
     */
    void increaseQuantity(T product);

}
