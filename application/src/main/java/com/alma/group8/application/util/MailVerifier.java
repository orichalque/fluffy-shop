package com.alma.group8.application.util;

/**
 * Created by Thibault on 26/11/2016.
 * Define a class verifying the validity of an email address.
 */
@FunctionalInterface
public interface MailVerifier {

    /**
     * Check if an email is valid
     * @param email the email to check
     * @return true if valid, false if not
     */
    boolean isValid(String email);
}
