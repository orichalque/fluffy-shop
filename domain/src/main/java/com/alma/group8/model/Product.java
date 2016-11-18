package com.alma.group8.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

/**
 * Product com.alma.group8.model class definition
 * Define the products sold or stored in the stock
 */
public class Product {

    private UUID id;
    private String name;
    private String description;
    private ProductType productType;
    private double price;
    private int quantity;

    /**
     * Return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set the field id
     *
     * @param id as java.util.UUID
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the field name
     *
     * @param name as java.lang.String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the field description
     *
     * @param description as java.lang.String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the productType
     */
    public ProductType getProductType() {
        return productType;
    }

    /**
     * Set the field productType
     *
     * @param productType as com.alma.group8.model.ProductType
     */
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    /**
     * Return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the field price
     *
     * @param price as double
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the field quantity
     *
     * @param quantity as int
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        return new EqualsBuilder()
                .append(price, product.price)
                .append(quantity, product.quantity)
                .append(id, product.id)
                .append(name, product.name)
                .append(description, product.description)
                .append(productType, product.productType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .append(productType)
                .append(description)
                .append(productType)
                .append(price)
                .append(quantity)
                .toHashCode();
    }



    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", productType=" + productType +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
