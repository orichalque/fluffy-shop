package com.alma.group8.api.interfaces;


import com.alma.group8.api.exceptions.FunctionalException;

/**
 * Created by Thibault on 15/11/16.
 * Define the Service implemented in the Domain Layer
 */
public interface ProductService  {

    /**
     * Reduce the quantity of a Product
     * @param product the product
     * @param quantity the quantity to remove
     * @throws FunctionalException if the quantity goes below 0
     * @return A serialized String of the product
     */
    String decreaseQuantity(String product, int quantity) throws FunctionalException;

    /**
     * Increase the quantity of a Product
     * @param product the serialized product to increase
     * @param quantity the quantity to increase
     * @return a serialized String of the product
     */
    String increaseQuantity(String product, int quantity) throws FunctionalException;

    /**
     * Verify a product to check if it is correct
     * @param product the product to verify
     * @throws FunctionalException the exception throwed when a product is not valid
     */
    void validate(String product) throws FunctionalException;

}
