package com.alma.group8.application.util;

/**
 * Created by Thibault on 24/11/16.
 * Define common variables for the application layer
 */
public class CommonVariables {

    public static final String ROOT_URL = "/api";
    public static final String ADMIN_URL = "/admin";


    public static final String SOAP_URL = "http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx?wsdl";
    public static final String SOAP_NAME = "EmailVerNoTestEmail";
    public static final String SOAP_ROOT = "http://ws.cdyne.com/";

    /**
     * Private constructor so this Util class cannot be instantiated
     */
    private CommonVariables() {

    }
}
