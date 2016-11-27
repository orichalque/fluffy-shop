package com.alma.group8.infrastructure.configuration;

import com.alma.group8.infrastructure.util.CommonVariables;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PreDestroy;

import static com.alma.group8.infrastructure.util.CommonVariables.MONGO_CLIENT;

/**
 * Define the Spring Configuration class. Instantiate the Spring Beans for accessing to the mongo database
 */
@Configuration
public class SpringMongoConfiguration{

    /**
     * Create a {@link MongoClient} and add in the the Spring Context
     * @return the created {@link MongoClient}
     */
    @Bean(name = MONGO_CLIENT)
    public MongoClient mongoClient() {
        MongoClientURI mongoClientURI = new MongoClientURI(String.format("%s/%s", CommonVariables.MONGO_CLIENT_URI, CommonVariables.MONGO_DATABASE_NAME));
        return new MongoClient(mongoClientURI);
    }

    /**
     * Create a {@link MongoCollection} from the {@link MongoClient} and add it in the Spring Context
     * @return the created {@link MongoCollection}
     */
    @DependsOn(MONGO_CLIENT)
    @Bean(name = "ProductCollection")
    public MongoCollection<Document> productCollection() {
        MongoCollection<Document> mongoCollection;
        mongoCollection= mongoClient().getDatabase(CommonVariables.MONGO_DATABASE_NAME).getCollection(CommonVariables.PRODUCT_COLLECTION_NAME).withDocumentClass(Document.class);

        return mongoCollection;
    }

    @DependsOn(MONGO_CLIENT)
    @Bean(name = "UserCollection")
    public MongoCollection<Document> userCollection() {
        MongoCollection<Document> mongoCollection;
        mongoCollection= mongoClient().getDatabase(CommonVariables.MONGO_DATABASE_NAME).getCollection(CommonVariables.USER_COLLECTION_NAME).withDocumentClass(Document.class);

        return mongoCollection;
    }

    /**
     * Close the {@link MongoClient}
     */
    @DependsOn(MONGO_CLIENT)
    @PreDestroy
    public void closeClient() {
        mongoClient().close();
    }

}
