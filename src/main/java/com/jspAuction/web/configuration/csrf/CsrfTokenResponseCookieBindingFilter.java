package com.jspAuction.web.configuration.csrf;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Created by sunit on 4/13/17.
 */
public class CsrfTokenResponseCookieBindingFilter extends OncePerRequestFilter {

    private static final String REQUEST_ATTRIBUTE_NAME = "_csrf";
    private static final String CSRF_TOKEN = "CSRF-TOKEN";
    private static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String csrfTokenValue = request.getHeader(X_CSRF_TOKEN);
        final Cookie[] cookies = request.getCookies();
        System.out.println("csrfTokenValue is "+csrfTokenValue);

        CsrfToken token = (CsrfToken) request.getAttribute(REQUEST_ATTRIBUTE_NAME);

        if(request.getRequestURI().equals("/login")||request.getRequestURI().equals("/logout")){
            Cookie cookie = new Cookie(CSRF.RESPONSE_COOKIE_NAME, token.getToken());
            cookie.setPath("/");

            response.addCookie(cookie);
            System.out.println("request is"+request.getAttribute(REQUEST_ATTRIBUTE_NAME));
            System.out.println("token is"+token.getHeaderName());
        }
        filterChain.doFilter(request, response);
    }
}