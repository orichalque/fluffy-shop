package com.alma.group8.converters;

import com.alma.group8.dto.ProductDTO;
import model.Product;


/**
 * Created by Thibault on 09/11/2016.
 * Convert a Product to a DTO Product
 */
public class ProductToProductDTOConverter {

    /**
     * convert method
     * @param product the product to convert
     * @return the converted productDTO
     */
    public ProductDTO convert(Product product) {
        ProductDTO productDTO = new ProductDTO();

        if (product.getId() != null) {
            productDTO.setId(product.getId().toString());
        }

        if (product.getProductType() != null) {
            productDTO.setProductType(ProductDTO.ProductType.fromValue(product.getProductType().toString()));
        }

        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());

        return productDTO;
    }

}
