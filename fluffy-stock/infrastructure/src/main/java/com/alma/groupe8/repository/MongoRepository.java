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
 * Created by E104607D on 04/11/16.
 */
public class MongoRepository implements ProductsRepository {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringMongoConfiguration.class);
        MongoCollection<?> mongoCollection = (MongoCollection<?>) annotationConfigApplicationContext.getBean("mongoCollection");


        System.out.println(mongoCollection.find().iterator().next().toString());

        annotationConfigApplicationContext.close();

    }

    @Autowired
    private MongoCollection<Product> mongoCollection;

    @Override
    public Product find(UUID uuid) throws ProductNotFoundException {
        return mongoCollection.find(eq("id", uuid.toString()), Product.class).first();
    }

    @Override
    public Collection<Product> findAll() {
        return Lists.newArrayList(mongoCollection.find());
    }

    @Override
    public void store(Product product) throws AlreadyExistingProductException {

    }

    @Override
    public void updateProduct(Product product) throws ProductNotFoundException {

    }

    @Override
    public void delete(UUID uuid) throws ProductNotFoundException {

    }
}
