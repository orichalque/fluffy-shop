package com.alma.group8.api.interfaces;

import com.alma.group8.api.exceptions.FunctionalException;

/**
 * Convert Business object to String and String to Business object
 * Created by Dennis on 25/11/16.
 */
public interface FunctionalFactory<T>{

    /**
     * Convert the string param to a business Object
     * @param metierAsJson the serialize object in Json stringify format
     * @return business object corresponding to the string param
     */
     T deserialize(String metierAsJson) throws FunctionalException;

    /**
     * Serialize a business object
     * @param object to serialize
     * @return the object in Json stringify
     */
     String serialize(T object) throws FunctionalException;
}
