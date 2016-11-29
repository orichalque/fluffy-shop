package com.alma.group8.infrastructure.repository;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.domain.model.Product;
import com.alma.group8.infrastructure.config.CommonTestVariables;
import com.alma.group8.infrastructure.util.CommonVariables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;
import org.junit.*;
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
@ContextConfiguration(classes = {MongoProductRepositoryTest.ContextConfiguration.class})
public class MongoProductRepositoryTest {

    @Autowired
    private MongoProductRepository mongoProductRepository;

    @Autowired
    public MongoCollection<Document> mongoCollection;

    @Configuration
    static class ContextConfiguration{
        @Bean
        public MongoCollection<Document> mongoCollection() {
            MongoClientURI mongoClientURI = new MongoClientURI(String.format("%s/%s", CommonVariables.MONGO_CLIENT_URI, CommonVariables.MONGO_DATABASE_NAME));
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            return mongoClient.getDatabase(CommonVariables.MONGO_DATABASE_NAME).getCollection(CommonTestVariables.PRODUCT_COLLECTION_NAME).withDocumentClass(Document.class);
        }

        @Bean
        public MongoProductRepository mongoRepository() {
            return new MongoProductRepository();
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
            mongoProductRepository.store(objectMapper.writeValueAsString(product));
        } catch (FunctionalException e) {
            Assert.fail("The database should be empty");
        } catch (JsonProcessingException e) {
            Assert.fail("The product should be parsable");
        }

        String productDTOAsString = null;

        try {
            productDTOAsString = mongoProductRepository.find(uuid.toString());
        } catch (FunctionalException e) {
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
            mongoProductRepository.store(productAsString);
        } catch (FunctionalException e) {
            Assert.fail("The database should not contain the product");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot read the product");
        }

        String currentProductInTheDatabaseAsJson = null;
        try {
            currentProductInTheDatabaseAsJson = mongoProductRepository.find(product.getId().toString());
        } catch (FunctionalException e) {
            Assert.fail();
        }

        try {
            Assert.assertEquals(product.getId(), objectMapper.readValue(currentProductInTheDatabaseAsJson, Product.class).getId());
        } catch (IOException e) {
            Assert.fail("Cannot read the product from the database");
        }

        try {
            mongoProductRepository.delete(product.getId().toString());
        } catch (FunctionalException e) {
            Assert.fail("The product should be in the database");
        }

        try {
            Assert.assertNull("The item should be in the database anymore", mongoProductRepository.find(product.getId().toString()));
            Assert.fail();
        } catch (FunctionalException e) {

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
            mongoProductRepository.store(objectMapper.writeValueAsString(product));
        } catch (FunctionalException e) {
            Assert.fail("The database should not contain the product");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the current Product");
        }

        product.setName("newName");

        try {
            mongoProductRepository.updateProduct(objectMapper.writeValueAsString(product));
        } catch (FunctionalException e) {
            Assert.fail("The product should be in the database");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the current Product");
        }

        String newProductAsString = null;
        try {
            newProductAsString = mongoProductRepository.find(product.getId().toString());
        } catch (FunctionalException e) {
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
            mongoProductRepository.store(objectMapper.writeValueAsString(product));
            mongoProductRepository.store(objectMapper.writeValueAsString(productDTO2));
        } catch (FunctionalException e) {
            Assert.fail("The database should not contain the product");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the product");
        }

        Assert.assertEquals("The database does not contain the elements", 2, mongoProductRepository.findAll().size());
    }

    @Test
    public void checkFindPage() {
        Product product = new Product();
        product.setId(UUID.randomUUID());

        Product productDTO2 = new Product();
        productDTO2.setId(UUID.randomUUID());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mongoProductRepository.store(objectMapper.writeValueAsString(product));
            mongoProductRepository.store(objectMapper.writeValueAsString(productDTO2));
        } catch (FunctionalException e) {
            Assert.fail("The database should not contain the product");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the product");
        }

        Assert.assertEquals("The database does not contain the elements", 1, mongoProductRepository.findPage(1, 1).size());
    }

    @Test(expected = FunctionalException.class)
    public void checkStoreSameElement() throws FunctionalException {
        Product product = new Product();
        product.setId(UUID.randomUUID());

        Product product1 = new Product();
        product1.setId(product.getId());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            mongoProductRepository.store(objectMapper.writeValueAsString(product));
        } catch (FunctionalException e) {
            Assert.fail("The repository should not contain this product already");
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the product");
        }

        try {
            mongoProductRepository.store(objectMapper.writeValueAsString(product1));
        } catch (JsonProcessingException e) {
            Assert.fail("Cannot serialize the product");
        }
    }

    @Test(expected = FunctionalException.class)
    public void checkDeleteNoElement() throws FunctionalException {
        mongoProductRepository.delete("uuid_not_in_the_database");
    }

    @After
    public void tearDown() {
        //Empty the database
        mongoCollection.deleteMany(new BsonDocument());
    }

}
