package com.jlaby.action.handlers;

/*
 * @(#)IActionHandler.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.action.*;
import com.jlaby.exception.*;

/**
 * Interface for handlers of actions. This interface
 * is needed only on the server side. NOTE: Implementations
 * of this interface must be completely thread-safe since
 * only one (Singleton) instance will be used to handle
 * all actions of each given type.
 *
 * @author  Marcel Schoen
 * @version $Id: IActionHandler.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public interface IActionHandler {

    /**
     * Carries out some action(s) based on the action object.
     *
     * @param action A LabyAction object created based on the client HTTP request.
     * @exception LabyException thrown if the action could not be handled.
     */
    public void performActionAsSoonAsPossibleBeforeItsTooLate(LabyAction action) throws LabyException;
}
