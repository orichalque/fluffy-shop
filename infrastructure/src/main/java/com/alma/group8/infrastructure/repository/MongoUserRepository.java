package com.alma.group8.infrastructure.repository;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.ProductsRepository;
import com.alma.group8.api.interfaces.UserRepository;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Thibault on 27/11/2016.
 * Define the user repository from the interface defined in the domain
 */
public class MongoUserRepository implements UserRepository {

    @Autowired
    private MongoCollection<Document> userCollection;

    private static final Logger LOGGER = Logger.getLogger(ProductsRepository.class);

    @Override
    public String find(String email) throws FunctionalException {
        LOGGER.info(String.format("Requesting the user database for the user with the email %s", email));
        Document document = userCollection.find(eq("mail", email)).first();
        String userFoundAsString;

        if (document != null) {
            document.remove("_id");
            userFoundAsString = document.toJson();
        } else {
            LOGGER.info("The user was not found");
            throw new FunctionalException("The user was not found");
        }

        return userFoundAsString;
    }

    @Override
    public Collection<String> findAll() {

        LOGGER.info("Requesting the user database for all users");
        List<Document> documentList = Lists.newArrayList(userCollection.find(Document.class));

        documentList.forEach(document -> document.remove("_id"));

        return Lists.transform(documentList, Document::toJson);
    }

    @Override
    public void insert(String userAsString) throws FunctionalException {

        LOGGER.info("Adding the user in the database");
        Document document = Document.parse(userAsString);
        String mail = (String) document.get("mail");

        if (userCollection.find(eq("mail", mail)).first() != null) {
            throw new FunctionalException("The user is already in the base");
        } else {
            userCollection.insertOne(document);
        }
    }

    @Override
    public void delete(String uuid) throws FunctionalException {
        LOGGER.info("Deleting a user in the database");
        String currentUser = find(uuid);

        if (Strings.isNullOrEmpty(currentUser)) {
            throw new FunctionalException("The user cannot be found in the database");
        } else {
            userCollection.deleteOne(Document.parse(currentUser));
        }
    }
}
