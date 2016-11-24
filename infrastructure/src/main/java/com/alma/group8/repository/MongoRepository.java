package com.alma.group8.repository;

import com.alma.group8.model.exceptions.AlreadyExistingProductException;
import com.alma.group8.model.exceptions.ProductNotFoundException;
import com.alma.group8.model.interfaces.ProductsRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
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
public class MongoRepository implements ProductsRepository {

    @Autowired
    private MongoCollection<Document> mongoCollection;

    @Override
    public String find(String uuid) throws ProductNotFoundException {
        //Returning the first item corresponding. the UUID are unique in the database so there is only 1 product, or none
        Document document = mongoCollection.find(eq("id", uuid)).first();

        String documentFoundAsString;
        if (document != null) {
            //MongoDb adds an index that we don't need
            document.remove("_id");
            documentFoundAsString = document.toJson();
        } else {
            throw new ProductNotFoundException(String.format("The product with the id %s is not in the database", uuid));
        }
        return documentFoundAsString;
    }

    @Override
    public Collection<String> findAll() {
        //Transform a List<Document> to a list<ProductDTO> and removes the _id property from Mongo
        List<Document> documentList = Lists.newArrayList(mongoCollection.find(Document.class));

        for (Document document : documentList) {
            document.remove("_id");
        }

        return Lists.transform(documentList, input -> input.toJson());
    }

    @Override
    public Collection<String> findPage(int page, int size) {
        //Skip the first results and returns the next page
        //Transform a List<Document> to a list<String> and removes the _id from mongoDb
        List<Document> documentList = Lists.newArrayList(mongoCollection.find().skip((page-1)*size).limit(size));

        for (Document document : documentList) {
            document.remove("_id");
        }

        return Lists.transform(documentList, input -> input.toJson());
    }

    @Override
    public void store(String product) throws AlreadyExistingProductException {
        Document document = Document.parse(product);

        Document currentProduct = mongoCollection.find(eq("id", document.get("id"))).first();

        if (currentProduct == null) {
            //Product found, when we only want to insert it
            mongoCollection.insertOne(Document.parse(product));
        } else {
            throw new AlreadyExistingProductException("The product already exists in the database, and thus cannot be stored");
        }
    }

    @Override
    public void updateProduct(String product) throws ProductNotFoundException {
        Document document = Document.parse(product);

        //We delete the products before inserting it again
        //The delete will throw the ProductNotFoundException if the product doesn't exist
        delete(document.getString("id"));

        mongoCollection.insertOne(document);
    }

    @Override
    public void delete(String uuid) throws ProductNotFoundException {
        String currentProduct = find(uuid);

        if (Strings.isNullOrEmpty(currentProduct)) {
            throw new ProductNotFoundException("The product cannot be found in the database");
        } else {
            mongoCollection.deleteOne(Document.parse(currentProduct));
       }
    }
}
