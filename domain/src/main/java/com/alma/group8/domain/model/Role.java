package com.alma.group8.domain.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thibault on 26/11/2016.
 * Enum defining the different roles of the {@link User}s
 */
public enum Role {
    ADMIN("admin"),
    CLIENT("client");

    private final String value;
    private static final Map<String, Role> CONSTANTS = new HashMap<>();

    static {
        for (Role c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Create a {@link Role} enum from the equivalent String value
     * @param value the value as a String
     * @return the {@link Role} enum object
     */
    public static Role fromValue(String value) {
        Role constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }
}
