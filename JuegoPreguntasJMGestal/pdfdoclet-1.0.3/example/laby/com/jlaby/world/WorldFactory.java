package com.jlaby.world;

/*
 * @(#)WorldFactory.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.world.exception.*;
import com.jlaby.action.*;

/**
 * This class creates a singleton world object instance
 * for a local world or a stub for a remote world.
 *
 * @author  Marcel Schoen
 * @version $Id: WorldFactory.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class WorldFactory {

    private static IWorld ms_world = null;
    private static String ms_currentURL = "";

    /**
     * Returns the reference to the Singleton instance
     * of the world object (or the world stub in case
     * of a remote client).
     *
     * @return the singleton world reference.
     * @exception thrown if the world had not been created yet.
     */
    public static IWorld getWorld() throws NotCreatedException {
        if(ms_world == null) {
            throw new NotCreatedException();
        }
        return ms_world;
    }

    /**
     * Creates a local world instance. This method is typically
     * only used once, by the Laby server / servlet during the
     * initialization process. Clients will use the <code>createRemoteWorld()</code>
     * method to communicate with such a world.
     *
     * @return a reference to the singleton world object instance.
     * @exception thrown if the world could not be created.
     */
    public static synchronized IWorld createLocalWorld() throws WorldException {
        if(ms_world == null) {
            ms_world = new com.jlaby.server.WorldImpl();
            ms_world.initialize();
        }
        return ms_world;
    }

    /**
     * Creates a stub for a HTTP connection to a remote
     * world.
     *
     * @param url the URL of the Laby world servlet.
     * @return a reference to the singleton world object instance (actually just a stub).
     * @exception thrown if the world could not be created.
     */
    public static IWorld createRemoteWorld(String url) throws WorldException {
        if(ms_world == null && url.equals(ms_currentURL) == false) {
            ms_currentURL = url;
            ms_world = new com.jlaby.client.stub.WorldStub(url);
            ms_world.initialize();
        }
        return ms_world;
    }

    /**
     * Creates a stub for a HTTP connection to a remote
     * world.
     *
     * @param url the URL of the Laby world servlet.
     * @param port the port number of the servlet.
     * @return a reference to the singleton world object instance (actually just a stub).
     * @exception thrown if the world could not be created.
     */
    public static IWorld createRemoteWorld(String url, int port) throws WorldException {
        return createRemoteWorld(url + ":" + String.valueOf(port));
    }
}
