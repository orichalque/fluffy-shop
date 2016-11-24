package com.alma.group8.model;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.FieldPredicate.exclude;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class ProductTest {

    private final Class<?> product = Product.class;


    @Test
    public void testAccessors() {
        assertPojoMethodsFor(product).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void testEquals() {
        assertPojoMethodsFor(product).testing(Method.EQUALS).areWellImplemented();
    }

    @Test
    public void testHashcode() {
        assertPojoMethodsFor(product, exclude("id")).testing(Method.HASH_CODE).areWellImplemented();
    }

    @Test
    public void testToString() {
        assertPojoMethodsFor(product).testing(Method.TO_STRING).areWellImplemented();
    }
}
