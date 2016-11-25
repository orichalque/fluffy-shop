package com.alma.group8.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Convert Business object to String and String to Business object
 * Created by Dennis on 25/11/16.
 */
public interface ProductFactory<Metier>{

    /**
     * Convert the string param to a business Object
     * @param dto the serialize object in Json stringify format
     * @return business object corresponding to the string param
     */
     Metier deserialize(String dto);

    /**
     * Serialize a business object
     * @param object to serialize
     * @return the object in Json stringify
     */
     String serialize(Metier object) throws JsonProcessingException;
}
