package com.alma.group8.application.controller;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.api.interfaces.UserRepository;
import com.alma.group8.application.util.CommonVariables;
import com.alma.group8.application.util.SoapMailVerifier;
import com.alma.group8.domain.exceptions.AlreadyExistingUserException;
import com.alma.group8.domain.exceptions.UserNotFoundException;
import com.alma.group8.domain.model.Role;
import com.alma.group8.domain.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Thibault on 27/11/2016.
 * User controller defining methods to get clients and admins of the database
 */
@CrossOrigin
@RestController
@RequestMapping(value = CommonVariables.ADMIN_URL)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FunctionalFactory userFactory;

    @Autowired
    private SoapMailVerifier soapMailVerifier;

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    /**
     * Get an user in the database from its email adress
     * @param email the email
     * @return the {@link com.alma.group8.domain.model.User}
     */
    @RequestMapping(value ="/user/{email}", method = RequestMethod.GET)
    @ResponseBody public String getUser(@PathVariable String email) throws FunctionalException {
        LOGGER.info(String.format("Receiving a GET Request to get an user from the email %s", email));

        try {
            return userRepository.find(email);
        } catch (FunctionalException e) {
            throw new UserNotFoundException(e);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody public String getAllUsers() {
        LOGGER.info("Receiving a GET Request to get all the users");

        Collection<String> userCollection = userRepository.findAll();
        String jsonArrayOfUsers = null;

        try {
            //The results serialized by the object mapper need some refactoring
            jsonArrayOfUsers = new ObjectMapper().writeValueAsString(userCollection).replace("\\", "");
            jsonArrayOfUsers = jsonArrayOfUsers.replace("\"{", "{");
            jsonArrayOfUsers = jsonArrayOfUsers.replace("}\"", "}");
        } catch (JsonProcessingException e) {
            LOGGER.warn("Cannot serialize the array of users", e);
        }

        return jsonArrayOfUsers;
    }

    /**
     * Create an admin and adds it in the database
     * @param email the mail of the admin
     * @throws FunctionalException
     */
    @RequestMapping(value = "/user/admin", method = RequestMethod.POST)
    public void insertAdmin(@RequestBody String email) throws FunctionalException {
        LOGGER.info(String.format("Receiving a POST Request to add an admin with the email %s", email));

        if (! soapMailVerifier.isValid(email)){
            throw new FunctionalException("Invalid email");
        }

        User user = new User();
        user.setMail(email);
        user.setRole(Role.ADMIN);

        String userAsString;

        userAsString = userFactory.serialize(user);

        try {
            userRepository.insert(userAsString);
        } catch (FunctionalException e) {
            throw new AlreadyExistingUserException(e);
        }
    }

    /**
     * Create a user and adds it in the database
     * @param email the email of the client
     * @throws FunctionalException
     */
    @RequestMapping(value = "/user/client", method = RequestMethod.POST)
    public void insertClient(@RequestBody String email) throws FunctionalException {
        LOGGER.info(String.format("Receiving a POST Request to add an client with the email %s", email));

        if (! soapMailVerifier.isValid(email)){
            throw new FunctionalException("Invalid email");
        }

        User user = new User();
        user.setMail(email);
        user.setRole(Role.CLIENT);

        String userAsString;

        userAsString = userFactory.serialize(user);

        try {
            userRepository.insert(userAsString);
        } catch (FunctionalException e) {
            throw new AlreadyExistingUserException(e);
        }
    }

}
