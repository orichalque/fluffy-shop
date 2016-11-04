package com.alma.groupe8.repository;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

/**
 * Created by E104607D on 04/11/16.
 */
public class MongoRepository {
    public static void main(String[] args) {
        MongoClientURI uri  = new MongoClientURI("mongodb://dennis:m2alma@ds143777.mlab.com:43777/fluffy-stock");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB(uri.getDatabase());
        DBCollection dbCollection = db.getCollection("products");

        System.out.println(dbCollection.count());
    }
}
