package com.alma.group8.infrastructure.repository;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.ProductsRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Thibault on 04/11/16.
 * ProductRepository implementation using MongoDB collections
 */
@Order(2)
@Repository
public class MongoProductRepository implements ProductsRepository {

    @Autowired
    private MongoCollection<Document> productCollection;

    private static final Logger LOGGER = Logger.getLogger(ProductsRepository.class);

    @Override
    public String find(String uuid) throws FunctionalException {
        LOGGER.info(String.format("Requesting the database for the product with the id %s", uuid));
        //Returning the first item corresponding. the UUID are unique in the database so there is only 1 product, or none
        Document document = productCollection.find(eq("id", uuid)).first();

        String documentFoundAsString;
        if (document != null) {
            //MongoDb adds an index that we don't need
            document.remove("_id");
            documentFoundAsString = document.toJson();
        } else {
            LOGGER.info("The product was not found");
            throw new FunctionalException(String.format("The product with the id %s is not in the database", uuid));
        }
        return documentFoundAsString;
    }

    @Override
    public Collection<String> findAll() {
        LOGGER.info("Requesting the database for all the products");
        //Transform a List<Document> to a list<ProductDTO> and removes the _id property from Mongo
        List<Document> documentList = Lists.newArrayList(productCollection.find(Document.class));

        for (Document document : documentList) {
            document.remove("_id");
        }

        return Lists.transform(documentList, Document::toJson);
    }

    @Override
    public Collection<String> findPage(int page, int size) {
        LOGGER.info("Requesting the database for a page");
        //Skip the first results and returns the next page
        //Transform a List<Document> to a list<String> and removes the _id from mongoDb
        List<Document> documentList = Lists.newArrayList(productCollection.find().skip((page-1)*size).limit(size));

        for (Document document : documentList) {
            document.remove("_id");
        }

        return Lists.transform(documentList, Document::toJson);
    }

    @Override
    public void store(String product) throws FunctionalException {
        LOGGER.info("Adding a product to the database");
        Document document = Document.parse(product);

        Document currentProduct = productCollection.find(eq("id", document.get("id"))).first();

        if (currentProduct == null) {
            //Product found, when we only want to insert it
            productCollection.insertOne(Document.parse(product));
        } else {
            throw new FunctionalException("The product already exists in the database, and thus cannot be stored");
        }
    }

    @Override
    public void updateProduct(String product) throws FunctionalException {
        LOGGER.info("Updating a product to the database");
        Document document = Document.parse(product);

        //We delete the products before inserting it again
        //The delete will throw the ProductNotFoundException if the product doesn't exist
        delete(document.getString("id"));

        productCollection.insertOne(document);
    }

    @Override
    public void delete(String uuid) throws FunctionalException {
        LOGGER.info("Deleting a product in the database");
        String currentProduct = find(uuid);

        if (Strings.isNullOrEmpty(currentProduct)) {
            throw new FunctionalException("The product cannot be found in the database");
        } else {
            productCollection.deleteOne(Document.parse(currentProduct));
       }
    }
}
