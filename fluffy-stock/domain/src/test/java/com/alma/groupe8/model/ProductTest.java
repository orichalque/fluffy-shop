package com.alma.groupe8.model;

import model.Product;
import model.exceptions.NotEnoughProductsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {

    private Product product;

    @Before
    public void setUp() {
        product = new Product();
        product.setQuantity(2);
    }

    @Test
    public void testIncreaseQuantity() {
        Assert.assertTrue(product.getQuantity() == 2);
        product.increaseQuantityBy(2);
        Assert.assertTrue(product.getQuantity() == 4);
    }

    @Test
    public void testDecreaseQuantity() {
        Assert.assertTrue(product.getQuantity() == 2);
        try {
            product.decreaseQuantityBy(2);
            Assert.assertTrue(product.getQuantity() == 0);
        } catch (NotEnoughProductsException e) {
            Assert.fail("The quantity is not enough to fail");
        }
    }

    @Test(expected=NotEnoughProductsException.class)
    public void testDecreaseQuantityThrow() throws NotEnoughProductsException {
        Assert.assertTrue(product.getQuantity() == 2);
        product.decreaseQuantityBy(5);
    }
}
