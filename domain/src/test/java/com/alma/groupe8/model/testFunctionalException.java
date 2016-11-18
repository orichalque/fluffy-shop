package com.alma.groupe8.model;

import com.alma.group8.model.exceptions.FunctionalException;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * Created by Thibault on 18/11/2016.
 * TU on {@link com.alma.group8.model.exceptions.FunctionalException} class
 */
public class testFunctionalException {

    private Class<?> functionalException = FunctionalException.class;

    @Test
    public void testConstructors() {
        assertPojoMethodsFor(functionalException).testing(Method.CONSTRUCTOR).areWellImplemented();
    }

    @Test
    public void testGetter() {
        assertPojoMethodsFor(functionalException).testing(Method.GETTER).areWellImplemented();
    }

    @Test
    public void testEquals() {
        assertPojoMethodsFor(functionalException).testing(Method.EQUALS).areWellImplemented();
    }

    @Test
    public void testHashCode() {
        assertPojoMethodsFor(functionalException).testing(Method.HASH_CODE).areWellImplemented();
    }
}
