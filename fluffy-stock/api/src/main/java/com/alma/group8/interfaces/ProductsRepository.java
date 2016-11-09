package com.alma.group8.interfaces;

import com.alma.group8.dto.ProductDTO;
import model.exceptions.AlreadyExistingProductException;
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
    ProductDTO find(String uuid);

    /**
     * Get all the products of the database
     * @return a List containing all the products, or an empty list if the database is empty
     */
    Collection<ProductDTO> findAll();

    /**
     * Store a product in the database
     * @param product the product to add
     * @throws AlreadyExistingProductException if the product already exists
     */
    void store(ProductDTO product) throws AlreadyExistingProductException;

    /**
     * Replace a product in the database with a new one, by comparing their IDs
     * @param product the product to update
     * @throws ProductNotFoundException if the product doesn't exist in the database
     */
    void updateProduct(ProductDTO product) throws ProductNotFoundException;

    /**
     * Delete the product in the database with the ID
     * @param uuid the id of the item to remove
     * @throws ProductNotFoundException if the uuid doesn't match with any product
     */
    void delete(String uuid) throws ProductNotFoundException;
}
