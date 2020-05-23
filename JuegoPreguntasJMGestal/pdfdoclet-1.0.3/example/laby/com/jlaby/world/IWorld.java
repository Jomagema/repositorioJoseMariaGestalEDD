package com.jlaby.world;

/*
 * @(#)IWorld.java 0.1 99/Feb/12
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
import com.jlaby.world.exception.*;

/**
 * This class loads and holds the world data.
 *
 * @author  Marcel Schoen
 * @version $Id: IWorld.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public interface IWorld {

    // **************************************************
    // CONSTANTS
    // **************************************************

    // Directions
    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;

    /**
     * Initializes the world (sounds nice, doesn't it?).
     * In other words, it does in a few milliseconds what
     * took god six days... so to speak...
     *
     * @throws InitializationFailedException if something went wrong and the
     *                                       world could not be created.
     */
    public void initialize() throws InitializationFailedException;

    /**
     * This method has to deal with an action created by
     * a "client" (local or remote). It has to have the
     * action handled by the appropriate handler and then
     * create an answer to be returned to the client.
     *
     * @param action the action created /sent by a client.
     * @return the answer for the client.
     * @exception LabyException thrown if the action could not be handled.
     */
    public LabyAnswer handleAction(LabyAction action) throws LabyException;
}
