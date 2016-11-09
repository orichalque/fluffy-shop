package com.alma.groupe8.repository;

import com.alma.group8.dto.ProductDTO;
import com.alma.groupe8.config.CommonTestVariables;
import com.alma.groupe8.util.CommonVariables;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import model.exceptions.AlreadyExistingProductException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public MongoCollection<ProductDTO> mongoCollection;

    @Configuration
    static class ContextConfiguration{
        @Bean
        public MongoCollection<ProductDTO> mongoCollection() {
            MongoClientURI mongoClientURI = new MongoClientURI(String.format("%s/%s", CommonVariables.MONGO_CLIENT_URI, CommonVariables.MONGO_DATABASE_NAME));
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoCollection<ProductDTO> mongoCollection = mongoClient.getDatabase(CommonVariables.MONGO_DATABASE_NAME).getCollection(CommonTestVariables.COLLECTION_NAME).withDocumentClass(ProductDTO.class);
            return mongoCollection;
        }

        @Bean
        public MongoRepository mongoRepository() {
            return new MongoRepository();
        }
    }

    @Before
    public void setUp() {
    }

    @Ignore
    @Test
    public void insertCheck() {
        //TODO: Replace domain object in the Repository by Gson DTO
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("name");
        productDTO.setDescription("description");
        try {
            mongoRepository.store(productDTO);
        } catch (AlreadyExistingProductException e) {
            e.printStackTrace();
        }
    }


}
