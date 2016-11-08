package com.alma.groupe8.repository;

import com.alma.groupe8.configuration.SpringMongoConfiguration;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import model.Product;
import model.ProductsRepository;
import model.exceptions.AlreadyExistingProductException;
import model.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Thibault on 04/11/16.
 * ProductRepository implementation using MongoDB collections
 */
public class MongoRepository implements ProductsRepository {

    @Autowired
    private MongoCollection<Product> mongoCollection;

    @Override
    public Product find(UUID uuid) {
        //Returning the first item corresponding. the UUID are unique in the database so there is only 1 product, or none
        return mongoCollection.find(eq("id", uuid.toString()), Product.class).first();
    }

    @Override
    public Collection<Product> findAll() {
        return Lists.newArrayList(mongoCollection.find());
    }

    @Override
    public void store(Product product) throws AlreadyExistingProductException {
        Product currentProduct = find(product.getId());
        if (currentProduct != null) {
            //Product found, when we only want to insert it
            mongoCollection.insertOne(product);
        } else {
            throw new AlreadyExistingProductException();
        }
    }

    @Override
    public void updateProduct(Product product) throws ProductNotFoundException {
        Product productFound = find(product.getId());
        if (productFound != null) {
            //We update it because it's already in the database
            //FIXME: Update instead of delete / insert
            delete(productFound.getId());
            mongoCollection.insertOne(product);
        } else {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public void delete(UUID uuid) throws ProductNotFoundException {
        Product product = find(uuid);

        if (product == null) {
            throw new ProductNotFoundException();
        } else {
            delete(uuid);
       }

    }
}
