package com.alma.groupe8.repository;

import com.alma.groupe8.config.CommonTestVariables;
import com.alma.groupe8.util.CommonVariables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.alma.group8.model.Product;
import com.alma.group8.model.exceptions.AlreadyExistingProductException;
import com.alma.group8.model.exceptions.ProductNotFoundException;
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

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Thibault on 09/11/2016.
 * Tests on the repository methods. Uses an alternative mongo collection called "test"
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoRepositoryTest.ContextConfiguration.class})
public class  MongoRepositoryTest {

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
    public void checkInsertAndFind() {
        Product product = new Product();
        product.setName("name");
        product.setDescription("description");

        UUID uuid = UUID.randomUUID();

        product.setId(uuid);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            mongoRepository.store(objectMapper.writeValueAsString(product));
        } catch (AlreadyExistingProductException e) {
            Assert.fail("The database should be empty");
        } catch (JsonProcessingException e) {
            Assert.fail("The product should be parsable");
        }

        String productDTOAsString = null;

        try {
            productDTOAsString = mongoRepository.find(uuid.toString());
        } catch (ProductNotFoundException e) {
            Assert.fail();
        }

        Product product1 = null;
        try {
             product1 = objectMapper.readValue(productDTOAsString, Product.class);
        } catch (IOException e) {
            Assert.fail("The product should be parsable");
        }

        Assert.assertEquals("The uuids are not the same", uuid, product1.getId());
        Assert.assertEquals("The name are not the same", product.getName(), product1.getName());
        Assert.assertEquals("The description are not the same", product.getDescription(), product1.getDescription());
    }

    @Test
    public void checkDelete() {
        Product product = new Product();
        product.setName("name");
        product.setDescription("description");

        UUID uuid = UUID.randomUUID();

        product.setId(uuid);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String productAsString = objectMapper.writeValueAsString(product);
            mongoRepository.store(productAsString);
        } catch (AlreadyExistingProductException e) {
            Assert.fail("The database should not contain the product");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot read the product");
        }

        String currentProductInTheDatabaseAsJson = null;
        try {
            currentProductInTheDatabaseAsJson = mongoRepository.find(product.getId().toString());
        } catch (ProductNotFoundException e) {
            Assert.fail();
        }

        try {
            Assert.assertEquals(product.getId(), objectMapper.readValue(currentProductInTheDatabaseAsJson, Product.class).getId());
        } catch (IOException e) {
            Assert.fail("Cannot read the product from the database");
        }

        try {
            mongoRepository.delete(product.getId().toString());
        } catch (ProductNotFoundException e) {
            Assert.fail("The product should be in the database");
        }

        try {
            Assert.assertNull("The item should be in the database anymore", mongoRepository.find(product.getId().toString()));
            Assert.fail();
        } catch (ProductNotFoundException e) {

        }
    }

    @Test
    public void checkUpdate() {
        Product product = new Product();
        product.setName("name");
        product.setDescription("description");

        UUID uuid = UUID.randomUUID();

        product.setId(uuid);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mongoRepository.store(objectMapper.writeValueAsString(product));
        } catch (AlreadyExistingProductException e) {
            Assert.fail("The database should not contain the product");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the current Product");
        }

        product.setName("newName");

        try {
            mongoRepository.updateProduct(objectMapper.writeValueAsString(product));
        } catch (ProductNotFoundException e) {
            Assert.fail("The product should be in the database");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the current Product");
        }

        String newProductAsString = null;
        try {
            newProductAsString = mongoRepository.find(product.getId().toString());
        } catch (ProductNotFoundException e) {
            Assert.fail();
        }

        try {
            Assert.assertEquals("The name should have been updated", "newName", objectMapper.readValue(newProductAsString, Product.class).getName());
        } catch (IOException e) {
            Assert.fail("Failure : cannot serialize the product");
        }
    }

    @Test
    public void checkFindAll() {
        Product product = new Product();
        product.setId(UUID.randomUUID());

        Product productDTO2 = new Product();
        productDTO2.setId(UUID.randomUUID());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mongoRepository.store(objectMapper.writeValueAsString(product));
            mongoRepository.store(objectMapper.writeValueAsString(productDTO2));
        } catch (AlreadyExistingProductException e) {
            Assert.fail("The database should not contain the product");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the product");
        }

        Assert.assertEquals("The database does not contain the elements", 2, mongoRepository.findAll().size());
    }

    @Test
    public void checkFindPage() {
        Product product = new Product();
        product.setId(UUID.randomUUID());

        Product productDTO2 = new Product();
        productDTO2.setId(UUID.randomUUID());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mongoRepository.store(objectMapper.writeValueAsString(product));
            mongoRepository.store(objectMapper.writeValueAsString(productDTO2));
        } catch (AlreadyExistingProductException e) {
            Assert.fail("The database should not contain the product");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the product");
        }

        Assert.assertEquals("The database does not contain the elements", 1, mongoRepository.findPage(1, 1).size());
    }

    @Test(expected = AlreadyExistingProductException.class)
    public void checkStoreSameElement() throws AlreadyExistingProductException {
        Product product = new Product();
        product.setId(UUID.randomUUID());

        Product product1 = new Product();
        product1.setId(product.getId());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            mongoRepository.store(objectMapper.writeValueAsString(product));
        } catch (AlreadyExistingProductException e) {
            Assert.fail("The repository should not contain this product already");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the product");
        }

        try {
            mongoRepository.store(objectMapper.writeValueAsString(product1));
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the product");
        }
    }

    @Test(expected = ProductNotFoundException.class)
    public void checkDeleteNoElement() throws ProductNotFoundException {
        mongoRepository.delete("uuid_not_in_the_database");
    }

    @After
    public void tearDown() {
        //Empty the database
        mongoCollection.deleteMany(new BsonDocument());
    }

}
