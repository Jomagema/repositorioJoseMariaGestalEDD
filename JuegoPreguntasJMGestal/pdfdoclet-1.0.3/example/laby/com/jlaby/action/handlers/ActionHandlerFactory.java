package com.jlaby.action.handlers;

/*
 * @(#)ActionHandlerFactory.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.action.*;
import com.jlaby.action.exception.*;
import com.jlaby.action.handlers.*;
import com.jlaby.config.*;

import java.util.*;

/**
 * This class creates laby action handler objects based on the
 * given action object. Every <code>LabyAction</code> class
 * defines its handler through its <code>getHandler()</code>
 * method return value.
 *
 * @author  Marcel Schoen
 * @version $Id: ActionHandlerFactory.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class ActionHandlerFactory {

    /**
     * Cache for IActionType objects. At VM runtime, one
     * instance of each type is needed.
     */
    private static Hashtable ms_actionHandlerObjects = new Hashtable();

    /**
     * Factory method which creates Singleton instances of handlers
     * for certain types of actions.
     *
     * @param actionObject the laby action for which a handler is required.
     * @return the handler for this action.
     * @exception UnsupportedActionException if no appropriate handler for the action could be created.
     *                                       One possible case is that the class could not be found.
     */
    public static IActionHandler getActionHandler(LabyAction actionObject) throws UnsupportedActionException {

        String actionHandlerClassName = actionObject.getHandler();
        IActionHandler handler = null;

        if(actionHandlerClassName != null) {
            Object obj = null;
            try {
                // First look in cache-Hashtable if object already exists
                if((obj = ms_actionHandlerObjects.get(actionHandlerClassName)) != null) {
                    handler = (IActionHandler)obj;
                } else {
                    // If not, load it and store it in Hashtable
                    Class typeClass = Class.forName(actionHandlerClassName);
                    obj = typeClass.newInstance();
                    handler = (IActionHandler)obj;
                    ms_actionHandlerObjects.put(actionHandlerClassName, handler);
                }
            } catch(Exception e) {
                throw new UnsupportedActionException(e);
            }
        }

        if(handler == null) {
            throw new UnsupportedActionException(actionHandlerClassName);
        }

        return handler;
    }
}
