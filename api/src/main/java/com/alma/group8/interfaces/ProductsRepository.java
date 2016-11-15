package com.alma.group8.interfaces;

import model.exceptions.AlreadyExistingProductException;
import model.exceptions.NotEnoughProductsException;
import model.exceptions.ProductNotFoundException;

import java.util.Collection;

/**
 * Interface defining the repository methods. Can be used by the application layer
 * regardless of the implementation in the infrastructure layer.
 */
public interface ProductsRepository {

    /**
     * find an item in the database using its name
     * @param uuid the id of the product
     * @return the product with the corresponding name
     */
    String find(String uuid);

    /**
     * Get all the products of the database
     * @return a List containing all the products, or an empty list if the database is empty
     */
    Collection<String> findAll();

    /**
     * Get all the products of a type from the database
     * @param type the products type
     * @return a List containing all the products, or an empty list if the database is empty
     */
    Collection<String> findProductsByType(String type);

    /**
     * Store a product in the database
     * @param product the product to add
     * @throws AlreadyExistingProductException if the product already exists
     */
    void store(String product) throws AlreadyExistingProductException;

    /**
     * Replace a product in the database with a new one, by comparing their IDs
     * @param product the product to update
     * @throws ProductNotFoundException if the product doesn't exist in the database
     */
    void updateProduct(String product) throws ProductNotFoundException;

    /**
     * Delete the product in the database with the ID
     * @param uuid the id of the item to remove
     * @throws ProductNotFoundException if the uuid doesn't match with any product
     */
    void delete(String uuid) throws ProductNotFoundException;

    /**
     * Reduce the quantity of the product in the database
     * @param uuid the id of the product
     * @param quantity the quantity of product to retrieve
     * @throws NotEnoughProductsException if the quantity ordered is superior to the quantity of the object
     * @return the product with the new quantity
     */
    String orderProduct(String uuid, int quantity) throws NotEnoughProductsException;
}
