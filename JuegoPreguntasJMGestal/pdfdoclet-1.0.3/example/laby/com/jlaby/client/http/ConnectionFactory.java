package com.jlaby.client.http;

/**
 * @(#)ConnectionFactory.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.client.exception.*;

import java.util.Properties;
import java.net.*;

/**
 * Helper class for the creation of HTTP connections.
 *
 * @author  Marcel Schoen
 * @version $Id: ConnectionFactory.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class ConnectionFactory {

    public static HttpURLConnection getHttpConnection(String urlstr) throws RemoteException {
        try {
            URL url = new URL(urlstr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            return con;
        } catch(Exception e) {
            throw new RemoteException(e);
        }
    }
}
