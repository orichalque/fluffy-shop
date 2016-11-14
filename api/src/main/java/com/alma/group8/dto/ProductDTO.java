package com.alma.group8.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfert Object defining the Product. Can be serialized into a Json with Google GSON
 */
@Generated("org.jsonschema2pojo")
public class ProductDTO {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("ProductType")
    @Expose
    private ProductDTO.ProductType productType;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     * The productType
     */
    public ProductDTO.ProductType getProductType() {
        return productType;
    }

    /**
     *
     * @param productType
     * The ProductType
     */
    public void setProductType(ProductDTO.ProductType productType) {
        this.productType = productType;
    }

    /**
     * Define the different product types
     */
    @Generated("org.jsonschema2pojo")
    public enum ProductType {

        DIVERS("divers");

        private final String value;
        private static final Map<String, ProductDTO.ProductType> CONSTANTS = new HashMap<>();

        static {
            for (ProductDTO.ProductType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ProductType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        /**
         * generate a ProductType from a string value
         * @param value the string
         * @return the corresponding enum value
         */
        public static ProductDTO.ProductType fromValue(String value) {
            ProductDTO.ProductType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}