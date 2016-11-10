package com.alma.groupe8.repository;

import com.alma.group8.dto.ProductDTO;
import com.alma.group8.interfaces.ProductsRepository;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import model.exceptions.AlreadyExistingProductException;
import model.exceptions.ProductNotFoundException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Thibault on 04/11/16.
 * ProductRepository implementation using MongoDB collections
 */
@Repository
public class MongoRepository implements ProductsRepository {

    private static final Gson GSON_MAPPER = new Gson();

    @Autowired
    private MongoCollection<Document> mongoCollection;

    @Override
    public ProductDTO find(String uuid) {
        //Returning the first item corresponding. the UUID are unique in the database so there is only 1 product, or none
        Document document = mongoCollection.find(eq("id", uuid)).first();

        ProductDTO productDto = null;

        if (document != null) {
            productDto = GSON_MAPPER.fromJson(document.toJson(), ProductDTO.class);
        }

        return productDto;
    }

    @Override
    public Collection<ProductDTO> findAll() {
        //Transform a List<Document> to a list<ProductDTO>
        List<ProductDTO> productDTOs = Lists.transform(Lists.newArrayList(mongoCollection.find(Document.class)),
                document -> GSON_MAPPER.fromJson(document.toJson(), ProductDTO.class));

        return productDTOs;
    }

    @Override
    public void store(ProductDTO product) throws AlreadyExistingProductException {
        ProductDTO currentProduct = find(product.getId());
        if (currentProduct == null) {
            //Product found, when we only want to insert it
            mongoCollection.insertOne(Document.parse(GSON_MAPPER.toJson(product)));
        } else {
            throw new AlreadyExistingProductException();
        }
    }

    @Override
    public void updateProduct(ProductDTO product) throws ProductNotFoundException {
        //We delete the products before inserting it again
        //The delete will throw the ProductNotFoundException if the product doesn't exist
        delete(product.getId());
        mongoCollection.insertOne(Document.parse(GSON_MAPPER.toJson(product)));
    }

    @Override
    public void delete(String uuid) throws ProductNotFoundException {
        ProductDTO productDTO = find(uuid);

        if (productDTO == null) {
            throw new ProductNotFoundException();
        } else {
            mongoCollection.deleteOne(Document.parse(GSON_MAPPER.toJson(productDTO)));
       }

    }
}
