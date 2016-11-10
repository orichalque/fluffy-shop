package com.alma.groupe8.converters;

import com.alma.group8.converters.ProductDTOToProductConverter;
import com.alma.group8.dto.ProductDTO;
import model.Product;
import model.ProductType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Thibault on 09/11/2016.
 * Test on the converter from DTO to Objects
 */
public class ProductDTOToProductConverterTest {
    private ProductDTOToProductConverter productDTOToProductConverter;

    @Before
    public void setUp() {
        productDTOToProductConverter = new ProductDTOToProductConverter();
    }

    @Test
    public void testConvertOk() {
        ProductDTO productDTO = new ProductDTO();
        UUID uuid = UUID.randomUUID();

        productDTO.setPrice(50.0);
        productDTO.setQuantity(5);
        productDTO.setName("Name");
        productDTO.setDescription("Description");
        productDTO.setId(uuid.toString());
        productDTO.setProductType(ProductDTO.ProductType.fromValue("divers"));

        Product productConverted = productDTOToProductConverter.convert(productDTO);

        Assert.assertEquals("Wrong name", "Name", productConverted.getName());
        Assert.assertEquals("Wrong description", "Description", productConverted.getDescription());
        Assert.assertEquals("Wrong quantity", 5, productConverted.getQuantity());
        Assert.assertEquals("Wrong price", 50.0, productConverted.getPrice(), 0.001);
        Assert.assertEquals("Wrong ID", uuid, productConverted.getId());
        Assert.assertEquals("Wrong Enum", ProductType.DIVERS, productConverted.getProductType());
    }

    @Test
    public void testConvertOkWithNoId() {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setPrice(50.0);
        productDTO.setQuantity(5);
        productDTO.setName("Name");
        productDTO.setDescription("Description");

        Product productConverted = productDTOToProductConverter.convert(productDTO);

        Assert.assertEquals("Wrong name", "Name", productConverted.getName());
        Assert.assertEquals("Wrong description", "Description", productConverted.getDescription());
        Assert.assertEquals("Wrong quantity", 5, productConverted.getQuantity());
        Assert.assertEquals("Wrong price", 50.0, productConverted.getPrice(), 0.001);
        Assert.assertEquals("Wrong ID", null, productConverted.getId());
    }
}
