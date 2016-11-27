package com.alma.group8.api.interfaces;

import com.alma.group8.api.exceptions.FunctionalException;

import java.util.Collection;

/**
 * Created by Thibault on 27/11/2016.
 * Define the methods usable by application layer to get the users of the Fluffy Stock API
 */
public interface UserRepository {

    /**
     * Find a user in the database from its email adress
     * @param email the mail of the user
     * @return the user as a String
     * @throws FunctionalException if no user found
     */
    String find(String email) throws FunctionalException;

    /**
     * Return all the users of the database
     * @return a Json as a string containing an array of users
     */
    Collection<String> findAll();

    /**
     * Insert an user in the database
     * @param userAsString the user as a String
     * @throws FunctionalException if the user already exists
     */
    void insert(String userAsString) throws FunctionalException;
}
