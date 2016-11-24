package com.alma.group8.model;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * Created by Thibault on 18/11/2016.
 * TU on {@link com.alma.group8.model.Error} class
 */
public class ErrorTest {

    private final Class<?> error = Error.class;

    @Test
    public void testAccessors() {
        assertPojoMethodsFor(error).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void testToString() {
        assertPojoMethodsFor(error).testing(Method.TO_STRING).areWellImplemented();
    }

    @Test
    public void testHashCode() {
        assertPojoMethodsFor(error).testing(Method.HASH_CODE).areWellImplemented();
    }

    @Test
    public void testEquals() {
        assertPojoMethodsFor(error).testing(Method.EQUALS).areWellImplemented();
    }

}
