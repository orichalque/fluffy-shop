package com.alma.group8.converters;

import com.alma.group8.dto.ProductDTO;
import com.google.common.base.Strings;
import model.Product;
import model.ProductType;

import java.util.UUID;

/**
 * Created by Thibault on 09/11/2016.
 */
public class ProductDTOToProductConverter {

    /**
     * Convert a productDTO To a product
     * @param productDTO
     * @return converted product
     * //FIXME not sure if DDD complies with an API depending on the domain
     */
    public Product convert(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());

        if (productDTO.getProductType() != null) {
            product.setProductType(ProductType.fromValue(productDTO.getProductType().toString()));
        }

        if (!Strings.isNullOrEmpty(productDTO.getId())) {
            product.setId(UUID.fromString(productDTO.getId()));
        }

        return product;
    }
}
