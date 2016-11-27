package com.alma.group8.infrastructure.repository;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.domain.model.Role;
import com.alma.group8.domain.model.User;
import com.alma.group8.infrastructure.config.CommonTestVariables;
import com.alma.group8.infrastructure.util.CommonVariables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by Thibault on 27/11/2016.
 * Tests the user repository
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoUserRepositoryTest.ContextConfiguration.class})
public class MongoUserRepositoryTest {

    @Autowired
    private MongoUserRepository mongoUserRepository;

    @Autowired
    public MongoCollection<Document> mongoCollection;

    @Configuration
    static class ContextConfiguration{
        @Bean
        public MongoCollection<Document> mongoCollection() {
            MongoClientURI mongoClientURI = new MongoClientURI(String.format("%s/%s", CommonVariables.MONGO_CLIENT_URI, CommonVariables.MONGO_DATABASE_NAME));
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            return mongoClient.getDatabase(CommonVariables.MONGO_DATABASE_NAME).getCollection(CommonTestVariables.USER_COLLECTION_NAME).withDocumentClass(Document.class);
        }

        @Bean
        public MongoUserRepository mongoRepository() {
            return new MongoUserRepository();
        }
    }

    @Before
    public void setUp() throws JsonProcessingException {
        mongoCollection.deleteMany(new BsonDocument());

        ObjectMapper mapper = new ObjectMapper();
        User user = new User();
        user.setMail("email");
        user.setRole(Role.ADMIN);
        mongoCollection.insertOne(Document.parse(mapper.writeValueAsString(user)));
        user = new User();
        user.setMail("email2");
        user.setRole(Role.CLIENT);
        mongoCollection.insertOne(Document.parse(mapper.writeValueAsString(user)));
    }

    @Test
    public void testFindAll() {
        Assert.assertEquals(2, mongoUserRepository.findAll().size());
    }

    @Test
    public void testFindOne() throws FunctionalException, IOException {
        String userAsString = mongoUserRepository.find("email");
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(userAsString, User.class);
        Assert.assertEquals(Role.ADMIN, user.getRole());
    }


    @Test(expected = FunctionalException.class)
    public void testFindOneNotInTheBase() throws FunctionalException {
        mongoUserRepository.find("emailNotInTheBase");
    }

    @Test
    public void testInsert() throws JsonProcessingException, FunctionalException {
        User user = new User();
        user.setMail("newMail");
        user.setRole(Role.CLIENT);
        String userAsString = new ObjectMapper().writeValueAsString(user);
        mongoUserRepository.insert(userAsString);

        mongoUserRepository.find("newMail");
    }

    @Test(expected = FunctionalException.class)
    public void testInsertProductInTheBase() throws JsonProcessingException, FunctionalException {
        User user = new User();
        user.setMail("email");
        user.setRole(Role.CLIENT);
        String userAsString = new ObjectMapper().writeValueAsString(user);
        mongoUserRepository.insert(userAsString);

    }
}
