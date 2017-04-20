package com.jspAuction.web.configuration.csrf;

/**
 * Created by sunit on 4/13/17.
 */
public class CSRF {
    /**
     * The name of the cookie with the CSRF token sent by the server as a response.
     */
    public static final String RESPONSE_COOKIE_NAME = "CSRF-TOKEN";
    /**
     * The name of the header carrying the CSRF token, expected in CSRF-protected requests to the server.
     */
    public static final String REQUEST_HEADER_NAME = "X-CSRF-TOKEN";
}
