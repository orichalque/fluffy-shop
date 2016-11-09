package com.alma.groupe8.repository;

import com.alma.group8.dto.ProductDTO;
import com.alma.groupe8.config.CommonTestVariables;
import com.alma.groupe8.util.CommonVariables;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import model.exceptions.AlreadyExistingProductException;
import org.bson.BsonDocument;
import org.bson.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * Created by Thibault on 09/11/2016.
 * Tests on the repository methods. Uses an alternative mongo collection
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoRepositoryTests.ContextConfiguration.class})
public class MongoRepositoryTests {

    @Autowired
    private MongoRepository mongoRepository;

    @Autowired
    public MongoCollection<Document> mongoCollection;

    @Configuration
    static class ContextConfiguration{
        @Bean
        public MongoCollection<Document> mongoCollection() {
            MongoClientURI mongoClientURI = new MongoClientURI(String.format("%s/%s", CommonVariables.MONGO_CLIENT_URI, CommonVariables.MONGO_DATABASE_NAME));
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            return mongoClient.getDatabase(CommonVariables.MONGO_DATABASE_NAME).getCollection(CommonTestVariables.COLLECTION_NAME).withDocumentClass(Document.class);
        }

        @Bean
        public MongoRepository mongoRepository() {
            return new MongoRepository();
        }
    }

    @Before
    public void setUp() {
        Assert.assertTrue("The database is not empty", mongoCollection.count() == 0);
    }

    @Test
    public void insertCheckAndFind() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("name");
        productDTO.setDescription("description");

        UUID uuid = UUID.randomUUID();

        productDTO.setId(uuid.toString());

        try {
            mongoRepository.store(productDTO);
        } catch (AlreadyExistingProductException e) {
            Assert.fail("The database should be empty");
        }

        ProductDTO productDTO1 = mongoRepository.find(uuid.toString());

        Assert.assertEquals("The uuids are not the same", uuid.toString(), productDTO1.getId());
        Assert.assertEquals("The name are not the same", productDTO.getName(), productDTO1.getName());
        Assert.assertEquals("The description are not the same", productDTO.getDescription(), productDTO1.getDescription());
    }

    @After
    public void tearDown() {
        //Empty the database
        mongoCollection.deleteMany(new BsonDocument());
    }

}
