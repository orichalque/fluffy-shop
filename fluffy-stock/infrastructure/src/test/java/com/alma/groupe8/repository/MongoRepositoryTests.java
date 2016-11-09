package com.alma.groupe8.repository;

import com.alma.groupe8.config.CommonTestVariables;
import com.alma.groupe8.util.CommonVariables;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by Thibault on 09/11/2016.
 * Tests on the repository methods. Uses an alternative mongo collection
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class MongoRepositoryTests {

    @Autowired
    private MongoRepository mongoRepository;

    @Autowired
    public MongoCollection<Product> mongoCollection;

    @Configuration
    static class ContextConfiguration{
        @Bean
        public MongoCollection<Product> mongoCollection() {
            MongoClientURI mongoClientURI = new MongoClientURI(String.format("%s/%s", CommonVariables.MONGO_CLIENT_URI, CommonVariables.MONGO_DATABASE_NAME));
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            MongoCollection<Product> mongoCollection = mongoClient.getDatabase(CommonVariables.MONGO_DATABASE_NAME).getCollection(CommonTestVariables.COLLECTION_NAME).withDocumentClass(Product.class);
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

    @Test
    public void insertCheck() {
        //TODO: Replace domain object in the Repository by Gson DTO
    }


}
