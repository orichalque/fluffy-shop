package com.alma.group8.interfaces;


/**
 * Created by Thibault on 15/11/16.
 * Define the Service implemented in the Domain Layer
 */
public interface ProductService<T extends Throwable>  {

    /**
     * Reduce the quantity of a Product
     * @param product the product
     * @param quantity the quantity to remove
     * @throws T if the quantity goes below 0
     * @return A serialized String of the product
     */
    String decreaseQuantity(String product, int quantity) throws T;

    /**
     * Increase the quantity of a Product
     * @param product the serialized product to increase
     * @param quantity the quantity to increase
     * @return a serialized String of the product
     */
    String increaseQuantity(String product, int quantity);

}
