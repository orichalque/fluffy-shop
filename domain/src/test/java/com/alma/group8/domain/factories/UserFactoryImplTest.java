package com.alma.group8.domain.factories;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.domain.model.Role;
import com.alma.group8.domain.model.User;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Thibault on 27/11/2016.
 * Test User factory
 */
public class UserFactoryImplTest {

    @Test
    public void testSerialize() {
        User user = new User();
        user.setMail("mail");
        user.setRole(Role.ADMIN);
        UserFactoryImpl userFactory = new UserFactoryImpl();
        try {
            String result = userFactory.serialize(user);
            Assert.assertEquals("{\"mail\":\"mail\",\"role\":\"ADMIN\"}", result);
        } catch (FunctionalException e) {
            Assert.fail();
        }
    }

    @Test
    public void testDeserialize() {
        UserFactoryImpl userFactory = new UserFactoryImpl();
        String userAsString = "{\"mail\":\"mail\",\"role\":\"ADMIN\"}";
        User user = null;
        try {
            user = userFactory.deserialize(userAsString);
        } catch (FunctionalException e) {
            Assert.fail();
        }
        Assert.assertEquals("mail", user.getMail());
        Assert.assertEquals(Role.ADMIN, user.getRole());
    }
}
