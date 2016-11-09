package com.alma.groupe8.converters;

import com.alma.group8.converters.ProductToProductDTOConverter;
import com.alma.group8.dto.ProductDTO;
import model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Thibault on 09/11/2016.
 * Test the ProductToProductDTOConverter class
 */
public class ProductToProductDTOConverterTest {

    private ProductToProductDTOConverter productToProductDTOConverter;

    @Before
    public void setUp() {
        productToProductDTOConverter = new ProductToProductDTOConverter();
    }

    @Test
    public void testConvertWithId() {
        UUID uuid = UUID.randomUUID();
        Product product = new Product();
        product.setName("Name");
        product.setPrice(50.0);
        product.setDescription("Description");
        product.setQuantity(5);
        product.setId(uuid);

        ProductDTO productDTO = productToProductDTOConverter.convert(product);

        Assert.assertEquals("Name not equal", "Name", productDTO.getName());
        Assert.assertEquals("Description not equal", "Description", productDTO.getDescription());
        Assert.assertEquals("Quantity not equal", 5, (long) productDTO.getQuantity());
        Assert.assertEquals("Price not equal", 50., productDTO.getPrice(), 0.01);
        Assert.assertEquals("id not equal", uuid.toString(), productDTO.getId());
    }

    @Test
    public void testConvertWithoutId() {
        Product product = new Product();
        product.setName("Name");
        product.setPrice(50.0);
        product.setDescription("Description");
        product.setQuantity(5);

        ProductDTO productDTO = productToProductDTOConverter.convert(product);

        Assert.assertEquals("Name not equal", "Name", productDTO.getName());
        Assert.assertEquals("Description not equal", "Description", productDTO.getDescription());
        Assert.assertEquals("Quantity not equal", 5, (long) productDTO.getQuantity());
        Assert.assertEquals("Price not equal", 50., productDTO.getPrice(), 0.01);
        Assert.assertNull("id not null", productDTO.getId());
    }
}
