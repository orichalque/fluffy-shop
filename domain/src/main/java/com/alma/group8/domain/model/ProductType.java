package com.alma.group8.domain.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Define the different possible types for the products stored in the stock
 * The usage of an enumeration instead of a specific java object allows to add types
 * after the creation of the com.alma.group8.domain, and increase the maintainability
 */
public enum ProductType {
    DIVERS("divers"),
    METAL("metal"),
    BOIS("bois"),
    MINERAL("mineral");


    private final String value;
    private static final Map<String, ProductType> CONSTANTS = new HashMap<>();

    static {
        for (ProductType c: values()) {
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
     * Create a ProductType enum from the equivalent String value
     * @param value the value as a String
     * @return the ProductType enum object
     */
    public static ProductType fromValue(String value) {
        ProductType constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
