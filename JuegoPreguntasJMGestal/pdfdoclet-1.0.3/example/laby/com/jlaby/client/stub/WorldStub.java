package com.jlaby.client.stub;

/*
 * @(#)WorldStub.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.util.*;

import com.jlaby.action.*;
import com.jlaby.client.*;
import com.jlaby.exception.*;
import com.jlaby.client.exception.*;
import com.jlaby.world.exception.*;
import com.jlaby.world.*;

/**
 * This is the stub used by a client to communicate
 * with a remote world on a server. The stub should
 * NOT be created directly but by using the factory
 * class <code>WorldFactory</code>.
 *
 * @author  Marcel Schoen
 * @version $Id: WorldStub.java,v 1.1 2013/10/27 23:51:27 marcelschoen Exp $
 */
public class WorldStub implements IWorld {

    private HttpStubHelper httpStub = null;

    /**
     * Constructs the stub object. DO NOT CALL
     * THIS CONSTRUCTOR DIRECTLY! Use the factory
     * method of the class <code>WorldFactory</code> instead.
     *
     * @param url the HTTP URL of the target laby server (servlet).
     * @see com.jlaby.world.WorldFactory
     */
    public WorldStub(String url) {
        httpStub = new HttpStubHelper(url);
    }

    /**
     * Constructs the stub object. DO NOT CALL
     * THIS CONSTRUCTOR DIRECTLY! Use the factory
     * method of the class <code>WorldFactory</code> instead.
     *
     * @param url the HTTP URL of the target laby server (servlet).
     * @port the port number of the server.
     * @see com.jlaby.world.WorldFactory
     */
    public WorldStub(String url, int port) {
        httpStub = new HttpStubHelper(url, port);
    }

    /**
     * Initializes the world access stub.
     *
     * @throws InitializationFailedException if something went wrong and the
     *                                       stub could not be created.
     */
    public void initialize() throws InitializationFailedException {
    }

    /**
     * This method serializes the action and sends it
     * to the "real" world server in a HTTP request
     * (Base64 encoded).
     *
     * @param action the action that should be handled by the server.
     * @return the answer sent from the server.
     * @exception LabyException thrown if the action could not be handled.
     */
    public LabyAnswer handleAction(LabyAction action) throws LabyException {
        try {
            return httpStub.sendAction(action);
        } catch(RemoteException e) {
            throw new LabyException(e);
        }
    }
}
