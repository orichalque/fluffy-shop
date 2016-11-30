package com.alma.group8.infrastructure.factories;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.domain.model.Product;
import com.alma.group8.domain.model.ProductType;
import com.alma.group8.domain.model.Role;
import com.alma.group8.domain.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Thibault on 28/11/2016.
 * Generic Factory Testing
 */
public class GenericFactoryTest {

    @Test
    public void serialize() throws FunctionalException {
        FunctionalFactory functionalFactory = new GenericFactory(User.class);
        User user = new User();
        user.setMail("mail");
        user.setRole(Role.ADMIN);
        Assert.assertEquals("{\"mail\":\"mail\",\"role\":\"ADMIN\"}", functionalFactory.serialize(user));
    }

    @Test
    public void deserialize() throws FunctionalException {
        FunctionalFactory functionalFactory = new GenericFactory(User.class);
        String userAsString = "{\"mail\":\"mail\",\"role\":\"ADMIN\"}";
        User user = (User) functionalFactory.deserialize(userAsString);
        Assert.assertEquals("mail", user.getMail());
        Assert.assertEquals(Role.ADMIN, user.getRole());
    }

    @Test(expected = FunctionalException.class)
    public void deserializeNok() throws FunctionalException {
        FunctionalFactory functionalFactory = new GenericFactory(User.class);
        String string = "{parameter : invalid}";
        functionalFactory.deserialize(string);
    }
}
