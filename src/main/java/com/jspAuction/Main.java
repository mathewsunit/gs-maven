package com.jspAuction;

import com.jspAuction.application.ApplicationServer;
import com.jspAuction.registration.RegistrationServer;
import com.jspAuction.web.WebServer;

/**
 * Created by sunit on 4/20/17.
 */
public class Main {

    public static void main(String[] args) {
        //Start Registartion Server
        RegistrationServer.main(args);
        //Start WebServer
        WebServer.main(args);
        //Start Application Server
        ApplicationServer.main(args);
    }
}