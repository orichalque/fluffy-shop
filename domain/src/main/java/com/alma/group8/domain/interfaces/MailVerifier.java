package com.alma.group8.domain.interfaces;

/**
 * Created by Thibault on 26/11/2016.
 * Define a class verifying the validity of an email address.
 * It is defined in the domain because this checking is a business necessity
 */
public interface MailVerifier {

    boolean isValid(String email);
}
