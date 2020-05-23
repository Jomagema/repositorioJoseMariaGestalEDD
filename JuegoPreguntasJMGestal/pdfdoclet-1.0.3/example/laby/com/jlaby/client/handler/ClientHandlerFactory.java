package com.jlaby.client.handler;

/*
 * @(#)ClientHandlerFactory.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */

import com.jlaby.log.*;

import javax.servlet.http.*;

/**
 * This class creates instances of client handlers for
 * client requests.
 *
 * @author  Marcel Schoen
 * @version $Id: ClientHandlerFactory.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class ClientHandlerFactory {

    public static final int BROWSER_CLIENT = 0;
    public static final int JAVA_CLIENT = 1;

    private static IClientHandler[] ms_clientHandler = null;

    /**
     * The static initializer creates one instance (Singleton)
     * of every client handler type.
     */
    static {
        ms_clientHandler = new IClientHandler[2];
        ms_clientHandler[0] = new BrowserClientHandler();
        ms_clientHandler[1] = new JavaClientHandler();
    }

    /**
     * This method returns an appropriate handler for a
     * given client request, based on the type of the client
     * (which is determined from the requests headers).
     * <P>
     * Supported HTTP header types so far:
     * <P>
     * <code>UserAgent:</code><BR>
     * Type: <code>JavaLabyClient/1.0 [classname]</code>
     * <P>
     * where <code>classname</code> can be empty or, theoretically,
     * the classname of an appropriate handler for the client. But
     * as of now, only one type of Java client handler exists and
     * this value will be ignored by the server.
     * <P>
     * If this HTTP header contains anything else than the value
     * described above, the server will assume that the client is
     * a web browser and use the <code>BrowserClientHandler</code>.
     *
     * @param request the HTTP servlet request sent from the client.
     */
    public static IClientHandler getClientHandler(HttpServletRequest request) {
        int clientType = BROWSER_CLIENT;
        String agent = request.getHeader("User-Agent");
        Log.info("ClientHandlerFactory", "getClientHandler", "Agent header: "+agent);
        if(agent != null) {
            if(agent.startsWith("JavaLabyClient")) {
                clientType = JAVA_CLIENT;
                Log.info("ClientHandlerFactory", "getClientHandler", "Use Java Client handler.");
            }
        }
        return ms_clientHandler[clientType];
    }
}
