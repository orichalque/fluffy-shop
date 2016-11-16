package com.alma.groupe8.model;

import model.Product;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.FieldPredicate.exclude;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class ProductTest {

    @Test
    public void pojoTest() {
        final Class<?> product = Product.class;
        //// FIXME: 15/11/2016 wont work with enum containing 1 value
        assertPojoMethodsFor(product, exclude("productType")).testing(Method.GETTER, Method.SETTER).areWellImplemented();
        //assertPojoMethodsFor(product, exclude("productType")).testing(Method.EQUALS).areWellImplemented();
    }
}
