/*
 * @(#)LabyServer.java
 *
 * @Copyright: Marcel Schoen, Switzerland, 2002, All Rights Reserved.
 */

package com.jlaby.server.jetty;

import org.mortbay.jetty.servlet.ServletHandlerContext;
import org.mortbay.jetty.Server;
import org.mortbay.http.handler.ForwardHandler;
import org.mortbay.http.handler.ResourceHandler;
import org.mortbay.util.InetAddrPort;
import org.mortbay.util.Frame;

import java.net.InetAddress;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.jlaby.config.Configuration;
import com.jlaby.server.servlet.LabyServlet;
import com.jlaby.server.*;
import com.jlaby.util.*;
import com.jlaby.log.*;

/** 
 * This Jetty-based server can run, depending on the configuration,
 * both the servlet(s) required to deal with requests from clients 
 * as well a an in-process instance of a Bot thread. This is especially 
 * useful for local testing in combination with the local pure Java
 * database "McKoi".
 * 
 * @author Marcel Schoen
 * @version $Id: LabyServer.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class LabyServer implements Runnable, ILabyConstants {

    private static boolean shutdownInitialized = false;
    private static Server server = null;

    private static LabyHttpListener remoteListener = null;
    private static LabyHttpListener localListener = null;
    private static ServletHandlerContext localHostContext = null;
    private static ServletHandlerContext remoteContext = null;

    /**
     * Constructs a server.
     */
    public LabyServer() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }

    /**
     * The main method which creates and invokes the server.
     */
    public static void main(String[] arg) {

        Throwable failureException = null;

        try {
            CurrentThread.add("main");

            LabyServlet.initialize();

            Log.info("LabyServer", "main", "LabyServer V." + 
                     LabyVersion.getVersion());
            Log.info("LabyServer", "main", "Build: " + 
                     LabyVersion.getBuildID() + " / " + 
                     LabyVersion.getBuildCVSTag());
            Log.info("LabyServer", "main", "Starting initialization...");

            // Configure Jetty Logger to log into our log file
            String logDir = Configuration.getProperty(PROP_LOG_DIR);
            if(!logDir.endsWith(File.separator)) {
                logDir = logDir + File.separator;
            }
            org.mortbay.util.Log.instance().add(new LogSinkAdapter());

            // Create Jetty server instance
            server = new Server();

            int remotePort, adminPort;
            if((remotePort = Configuration.getNumericProperty(PROP_SERVER_PORT)) < 0) {
                remotePort = 8080;
            }
            adminPort = remotePort + 1;

            Log.info("LabyServer", "main", "Creating HTTP listener for remote clients on port:"+String.valueOf(remotePort));
            remoteListener = new LabyHttpListener(new InetAddrPort(InetAddress.getLocalHost().getHostName(),remotePort));

            Log.info("LabyServer", "main", "Creating HTTP listener for 127.0.0.1 on port:"+String.valueOf(adminPort));
            localListener = new LabyHttpListener(new InetAddrPort(InetAddress.getByName("127.0.0.1"),adminPort));

            server.addListener(localListener);
            server.addListener(remoteListener);
            
            Log.trace("LabyServer", "main", "Creating admin context");

            ForwardHandler forwarder = new ForwardHandler();
            forwarder.setRootForward("laby");

            ResourceHandler pageHandler = new ResourceHandler();
            pageHandler.setDirAllowed(true);
            pageHandler.addIndexFile("welcome.html");

            // Let the HTTPS/SSL listener forward all requests to the AdminServlet
            localHostContext = (ServletHandlerContext)server.getContext("127.0.0.1", "/");
            localHostContext.addServlet("laby","/laby","com.jlaby.server.servlet.LabyServlet");

            // Let the HTTPS/SSL listener forward all requests to the AdminServlet
            remoteContext=(ServletHandlerContext)server.getContext(null,"/");
            remoteContext.setResourceBase(".");
            remoteContext.setServingResources(true);
            remoteContext.addHandler(pageHandler);
            remoteContext.addServlet("laby","/laby","com.jlaby.server.servlet.LabyServlet");

            //            Log.trace("LabyServer", "main", "Initialize admin servlet...");
            //            AdminServlet.initialize();
                                         
            // Start handlers and listener
            Log.info("LabyServer", "main", "Starting up server...");
            server.start(); 

            Log.info("LabyServer", "main", "Initialization completed. Server ready.");
          
        } catch(RuntimeException e) {
            failureException = e;
        } catch(Exception e) {
            failureException = e;
        } catch(Error e) {
            failureException = e;
        } catch(Throwable e) {
            failureException = e;
        }

        if(failureException != null) {

            // NOTE: Since the Jetty server started above is multi threaded,
            // running into this catch clause can mean only one thing: The
            // start of the server failed. Any exception thrown during the
            // server's lifetime will have to be handled by the main servlet.

            System.out.println("FAILED! Server not started!");

            try {
                // Try to log error if possible. This may fail as well and even
                // create new exceptions (depending on the source of the problem) 
                // but that won't hurt much since the server has failed to operate
                // at this point anyway. However, If it works, it may be helpful.
                Log.error("LabyServer", "main", "Server not started due to exception " + failureException);
                failureException.printStackTrace();
            } catch(Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Start failed due to exception " + failureException);

            // exit VM with error return code.
            Log.warning("LabyServer", "main", "Exit Java VM with returncode -1");
            System.out.println("Exit Java VM with returncode -1");
            System.exit(-1);
        }
    }

    /**
     * Deletes all files with a given prefix in a 
     * given directory. The purpose of this method
     * is to clean up temp stuff left over from an
     * earlier run.
     *
     * @param directory The path of the directory with the files.
     * @param prefix The prefix of the files that shall be deleted.
     */
    private static void deleteFiles(String directory, String prefix) {
        File dir = new File(directory);
        if(dir.exists() && dir.isDirectory()) {
            String[] files = dir.list();
            for(int i=0; i<files.length; i++) {
                if(files[i].startsWith(prefix)) {
                    File file = new File(directory, files[i]);
                    file.delete();
                }
            }
        }
    }

    /**
     * Ensure a VM exit after a given maximum time.
     */
    public static void beginShutdown() {
        Thread shutdownThread = new Thread(new LabyServer());
        shutdownThread.start();
    }

    /**
     * Shuts down the Jetty server by stopping and removing
     * all listeners first and stopping and destroying the
     * server subsequently. This method implements a somewhat
     * "heavy" exception handling, the reason being the fact
     * that the shutdown must be as robust and safe as possible.
     */
    public static void destroy() {

        if(!shutdownInitialized) {

            shutdownInitialized = true;

            Log.trace("LabyServer", "destroy", "Destroying server...");
            try {
                localListener.destroy();
                remoteListener.destroy();
                server.removeContext(localHostContext);
                server.removeContext(remoteContext);
                server.removeListener(localListener);
                server.removeListener(remoteListener);
                server.stop();
                server.destroy();
            } catch(Throwable e) {
                Log.warning("LabyServer", "destroy", "Exception caught (d): "+e.getClass().getName()+"("+e.getMessage()+")");
            }

            Log.trace("LabyServer", "destroy", "Server destroyed.");
        }
    }

    /**
     * This Thread ensures that when a shutdown has been
     * initialized, the Java VM will be terminated after
     * max. 15 seconds, under all circumstances, no matter 
     * what happens in any other thread.
     * 
     * PLEASE NOTE: If the program is in self-testing mode,
     * it will NOT exit (as other tests may be made subsequently 
     * in the same VM instance).
     */
    public void run() {
        try {
            CurrentThread.add("shutdown");
            Thread.sleep(15000);
        } catch(Throwable e) {
        }
    }

    /**
     * This class ensures that the server is destroyed
     * cleanly should the JVM be killed with a signal (CTRL-C).
     */
    public class ShutdownHook extends Thread {
        public void run() {
            CurrentThread.add("shutdownHook");
            LabyServer.destroy();
            CurrentThread.remove();
        }
    }


}

