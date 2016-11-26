package com.alma.group8.domain;

import com.alma.group8.domain.model.User;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * Created by Thibault on 26/11/2016.
 * User test class
 */
public class UserTest {

    private final Class<?> user = User.class;

    @Test
    public void testAccessors() {
        assertPojoMethodsFor(user).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void testEquals() {
        assertPojoMethodsFor(user).testing(Method.EQUALS).areWellImplemented();
    }

    @Test
    public void testToString() {
        assertPojoMethodsFor(user).testing(Method.TO_STRING).areWellImplemented();
    }
}
