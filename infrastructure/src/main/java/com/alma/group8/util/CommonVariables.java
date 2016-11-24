package com.alma.group8.util;

/**
 * Created by Thibault on 04/11/2016.
 * Define common variables for the infrastructure layer
 */
public class CommonVariables {

    public static final String MONGO_CLIENT_URI = "mongodb://orichalque:m2alma@ds139567.mlab.com:39567";
    public static final String MONGO_DATABASE_NAME = "fluffystock";
    public static final String COLLECTION_NAME = "products";
    public static final String MONGO_CLIENT = "mongoClient";
    /**
     * This class should only offer static variables, a private constructor is needed
     */
    private CommonVariables() {

    }


}
