package com.jlaby.action;

/*
 * @(#)LabyAction.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.action.exception.*;
import com.jlaby.character.*;
import com.jlaby.exception.*;

import java.util.*;
import java.io.*;

/**
 * Default implementation of an action object.
 *
 * @author  Marcel Schoen
 * @version $Id: LabyAction.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 */
public abstract class LabyAction implements Serializable {

    // **************************************************
    // CONSTANTS
    // **************************************************
    public static final int PUBLIC_ACTION = 1;
    public static final int AUTHENTICATED_ACTION = 2;

    // **************************************************
    // VARIABLES
    // **************************************************

    /**
     * The originator of the action.
     */
    private GameCharacter m_source = null;

    /**
     * Returns the name of the handler for the action.
     *
     * @returns The class name of the handler for this action.
     */
    public abstract String getHandler();

    /**
     * Must return the type of the action (<code>PUBLIC_ACTION</code>
     * or <code>AUTHENTICATED_ACTION</code>). This way the class
     * defines itself to which type of action it belongs.
     *
     * @return the type of the action.
     */
    public abstract int getType();

    /**
     * Returns the originator (creator) of the action.
     * 
	 * @deprecated The definition of this call depends on {@link #suspend},
     *         which is deprecated.  Further, the results of this call
     *         were never well-defined.
	 *
     * @returns A reference to the GameCharacter object that created the action
     *          or null in case of anonymous (public) actions.
     */
    public final GameCharacter getSource() {
        return m_source;
    }

    /**
     * Sets the originator of the action.
     *
     * @returns A reference to the GameCharacter object that created the action.
     */
    public final void setSource(GameCharacter character) {
        m_source = character;
    }
}
