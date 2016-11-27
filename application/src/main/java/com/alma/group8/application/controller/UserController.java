package com.alma.group8.application.controller;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.ProductFactory;
import com.alma.group8.application.util.CommonVariables;
import com.alma.group8.domain.interfaces.UserRepository;
import com.alma.group8.domain.model.Product;
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
    UserRepository userRepository;

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    /**
     * Get an user in the database from its email adress
     * @return the {@link com.alma.group8.domain.model.User}
     */
    @RequestMapping(value ="/user/{email}", method = RequestMethod.GET)
    @ResponseBody public String getUser(@PathVariable String email) throws FunctionalException {
        //FIXME invalid exception
        LOGGER.info(String.format("Receiving a GET Request to get an user from the email %s", email));
        return userRepository.find(email);
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

    @RequestMapping(value = "/user/admin", method = RequestMethod.POST)
    public void insertAdmin(@RequestBody String email) throws FunctionalException {
        //FIXME Invalid exception
        User user = new User();
        user.setMail(email);
        user.setRole(Role.ADMIN);
        //FIXME use factory

        String userAsString = null;
        try {
            userAsString = new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new FunctionalException(e);
        }
        userRepository.insert(userAsString);
    }

    @RequestMapping(value = "/user/client", method = RequestMethod.POST)
    public void insertClient(@RequestBody String email) throws FunctionalException {
        //FIXME Invalid exception
        User user = new User();
        user.setMail(email);
        user.setRole(Role.CLIENT);
        //FIXME use factory

        String userAsString = null;
        try {
            userAsString = new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new FunctionalException(e);
        }
        userRepository.insert(userAsString);
    }

}
