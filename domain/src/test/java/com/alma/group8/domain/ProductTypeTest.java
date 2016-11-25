package com.alma.group8.domain;

import com.alma.group8.domain.model.ProductType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Thibault on 18/11/2016.
 * Test the {@link ProductType} enum
 */
public class ProductTypeTest {

    @Test
    public void testToString() {
        ProductType productType = ProductType.BOIS;

        Assert.assertEquals("ToString non fonctionnel", "bois", productType.toString());
        Assert.assertNotEquals("ToString non fonctionnel", "mineral", productType.toString());
    }

    @Test
    public void testFromValue() {
        ProductType productType = ProductType.fromValue("bois");

        Assert.assertEquals("From value non fonctionnel", productType, ProductType.BOIS);
        Assert.assertNotEquals("From value non fonctionnel", productType, ProductType.DIVERS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromValueNull() {
        ProductType.fromValue(null);
    }

}
