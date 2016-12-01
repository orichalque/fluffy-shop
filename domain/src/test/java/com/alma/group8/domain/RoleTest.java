package com.alma.group8.domain;

import com.alma.group8.domain.model.ProductType;
import com.alma.group8.domain.model.Role;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Thibault on 26/11/2016.
 * Role enum test class
 */
public class RoleTest {

        @Test
        public void testToString() {
            Role role = Role.ADMIN;

            Assert.assertEquals("ToString non fonctionnel", "admin", role.toString());
            Assert.assertNotEquals("ToString non fonctionnel", "client", role.toString());
        }

        @Test
        public void testFromValue() {
            Role role = Role.fromValue("admin");

            Assert.assertEquals("From value non fonctionnel", role, Role.ADMIN);
            Assert.assertNotEquals("From value non fonctionnel", role, Role.CLIENT);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testFromValueNull() {
            Role.fromValue(null);
        }
}
