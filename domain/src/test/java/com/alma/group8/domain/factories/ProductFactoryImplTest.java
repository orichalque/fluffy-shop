package com.alma.group8.domain.factories;

import com.alma.group8.api.exceptions.FunctionalException;
import com.alma.group8.api.interfaces.FunctionalFactory;
import com.alma.group8.domain.model.Product;
import com.alma.group8.domain.model.ProductType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Thibault on 26/11/2016.
 * Test the product factory impl
 */
public class ProductFactoryImplTest {

    private FunctionalFactory functionalFactory;

    @Before
    public void setUp() {
        functionalFactory = new ProductFactoryImpl();
    }

    @Test
    public void testSerialize() throws FunctionalException {
        Product product = new Product();
        product.setName("product");
        product.setDescription("description");
        product.setPrice(50.);
        UUID uuid = UUID.fromString("3b80708b-447f-43a7-94ce-ff271353c52a");
        product.setId(uuid);
        product.setProductType(ProductType.BOIS);
        product.setQuantity(10);

        String productAsString = functionalFactory.serialize(product);
        Assert.assertEquals("{\"id\":\"3b80708b-447f-43a7-94ce-ff271353c52a\",\"name\":\"product\",\"description\":\"description\",\"productType\":\"BOIS\",\"price\":50.0,\"quantity\":10}", productAsString);
    }

    @Test
    public void testDeserialize() throws FunctionalException {
        String productAsString = "{\"id\":\"3b80708b-447f-43a7-94ce-ff271353c52a\",\"name\":\"product\",\"description\":\"description\",\"productType\":\"BOIS\",\"price\":50.0,\"quantity\":10}";
        Product product = (Product) functionalFactory.deserialize(productAsString);

        Assert.assertEquals("product", product.getName());
        Assert.assertEquals("description", product.getDescription());
        Assert.assertEquals("3b80708b-447f-43a7-94ce-ff271353c52a", product.getId().toString());
        Assert.assertEquals(10, product.getQuantity());
        Assert.assertEquals(ProductType.BOIS, product.getProductType());
        Assert.assertEquals(50., product.getPrice(), 0.1);
    }
}
