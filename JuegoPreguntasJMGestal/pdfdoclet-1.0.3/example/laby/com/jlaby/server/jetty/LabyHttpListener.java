/*
 * @(#)LabyHttpListener.java
 *
 * @Copyright: TARSEC AG, Switzerland, 2002, All Rights Reserved.
 */

package com.jlaby.server.jetty;

import org.mortbay.http.HttpListener;
import org.mortbay.http.HttpRequest;
import org.mortbay.http.HttpServer;
import org.mortbay.http.HttpConnection;
import org.mortbay.util.InetAddrPort;
import org.mortbay.http.SocketListener;
import java.net.ServerSocket;

import com.jlaby.log.Log;

/**
 * 
 *
 * @author  Marcel Schoen
 * @version $Id: LabyHttpListener.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class LabyHttpListener implements HttpListener {

    private String options = "";
    private boolean isStarted = false;
    private boolean isDestroyed = false;

    private SocketListener listener = null;

    public LabyHttpListener(InetAddrPort addr) throws java.io.IOException {
        Log.trace(this, "Constructor", "Created new HTTP listener for address " + addr);
        listener = new SocketListener(addr);
    }

    public LabyHttpListener() {
        Log.trace(this, "Constructor", "Created new HTTP listener.");
        listener = new SocketListener();
    }


    public void setHttpServer(HttpServer server) {
        Log.trace(this, "setHttpServer", server.toString());
        listener.setHttpServer(server);
    }

    public void setHost(String host) throws java.net.UnknownHostException {
        Log.trace(this, "setHost", host);
        listener.setHost(host);
    }

    public void setPort(int port) {
        Log.trace(this, "setPort", String.valueOf(port));
        listener.setPort(port);
    }

    public void persistConnection(HttpConnection connection) {
        Log.trace(this, "persistConnection", "...");
        listener.persistConnection(connection);
    }

    public boolean isOutOfResources() {
        Log.trace(this, "isOutOfResources", String.valueOf(listener.isOutOfResources()));
        return listener.isOutOfResources();
    }

    public boolean isLowOnResources() {
        Log.trace(this, "isLowOnResources", String.valueOf(listener.isLowOnResources()));
        return listener.isLowOnResources();
    }

    public ServerSocket getServerSocket() {
        Log.trace(this, "getServerSocket", "Create new socket");
        return listener.getServerSocket();
    }

    public int getPort() {
        Log.trace(this, "getPort", String.valueOf(listener.getPort()));
        return listener.getPort();
    }

    public HttpServer getHttpServer() {
        Log.trace(this, "getHttpServer", listener.getHttpServer().toString());
        return listener.getHttpServer();
    }

    public String getHost() {
        Log.trace(this, "getHost", listener.getHost());
        return listener.getHost();
    }

    public String getDefaultScheme() {
        Log.trace(this, "getDefaultScheme", listener.getDefaultScheme());
        return listener.getDefaultScheme();
    }

    public void customizeRequest(HttpConnection connection,
                                 HttpRequest request)
    {
        Log.trace(this, "customizeRequest", "Request: "+request);
        Log.trace(this, "customizeRequest", "Connection: "+connection);
        listener.customizeRequest(connection, request);
    }

    //****************************************
    // LifeCycle interface
    //****************************************
    
    public void start() throws java.lang.Exception {
        Log.trace(this, "start", "Start now..");
        listener.start();
    }

    public void stop() throws java.lang.InterruptedException {
        Log.trace(this, "stop", "Stop now..");
        listener.stop();
    }

    public void destroy() {
        Log.trace(this, "destroy", "Gets destroyed now");
        listener.destroy();
    }

    public boolean isDestroyed() {
        Log.trace(this, "isDestroyed", "Is destroyed: "+String.valueOf(listener.isDestroyed()));
        return listener.isDestroyed();
    }

    public boolean isStarted() {
        Log.trace(this, "isStarted", "Is started: "+String.valueOf(listener.isStarted()));
        return listener.isStarted();
    }
}
