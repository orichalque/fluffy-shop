package com.alma.groupe8.repository;

import com.alma.group8.dto.ProductDTO;
import com.alma.group8.interfaces.ProductsRepository;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import model.exceptions.AlreadyExistingProductException;
import model.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Thibault on 04/11/16.
 * ProductRepository implementation using MongoDB collections
 */
@Repository
public class MongoRepository implements ProductsRepository {

    @Autowired
    private MongoCollection<ProductDTO> mongoCollection;

    @Override
    public ProductDTO find(String uuid) {
        //Returning the first item corresponding. the UUID are unique in the database so there is only 1 product, or none
        return mongoCollection.find(eq("id", uuid), ProductDTO.class).first();
    }

    @Override
    public Collection<ProductDTO> findAll() {
        return Lists.newArrayList(mongoCollection.find(ProductDTO.class));
    }

    @Override
    public void store(ProductDTO product) throws AlreadyExistingProductException {
        ProductDTO currentProduct = find(product.getId());
        if (currentProduct != null) {
            //Product found, when we only want to insert it
            mongoCollection.insertOne(product);
        } else {
            throw new AlreadyExistingProductException();
        }
    }

    @Override
    public void updateProduct(ProductDTO product) throws ProductNotFoundException {
        //We delete the products before inserting it again
        //The delete will throw the ProductNotFoundException if the product doesn't exist
        delete(product.getId());
        mongoCollection.insertOne(product);
    }

    @Override
    public void delete(String uuid) throws ProductNotFoundException {
        ProductDTO productDTO = find(uuid);

        if (productDTO == null) {
            throw new ProductNotFoundException();
        } else {
            delete(uuid);
       }

    }
}
