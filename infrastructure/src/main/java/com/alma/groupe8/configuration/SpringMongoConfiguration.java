package com.alma.groupe8.configuration;

import com.alma.groupe8.util.CommonVariables;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Define the Spring Configuration class. Instantiate the Spring Beans for accessing to the mongo database
 */
@Configuration
public class SpringMongoConfiguration{

    @Bean
    public MongoCollection<Document> mongoCollection() {
        MongoCollection<Document> mongoCollection;

        MongoClientURI mongoClientURI = new MongoClientURI(String.format("%s/%s", CommonVariables.MONGO_CLIENT_URI, CommonVariables.MONGO_DATABASE_NAME));
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        mongoCollection= mongoClient.getDatabase(CommonVariables.MONGO_DATABASE_NAME).getCollection(CommonVariables.COLLECTION_NAME).withDocumentClass(Document.class);

        mongoClient.close();
        return mongoCollection;
    }
}
