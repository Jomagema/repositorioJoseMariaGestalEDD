package com.jlaby.action.handlers;

/*
 * @(#)NoActionHandler.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.action.*;
import com.jlaby.action.actions.*;
import com.jlaby.action.exception.*;
import com.jlaby.exception.*;
import com.jlaby.character.*;
import com.jlaby.log.*;

/**
 * This class handles "no" actions (created
 * through requests to the Laby servlet without
 * action parameters).
 *
 * @author  Marcel Schoen
 * @version $Id: NoActionHandler.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class NoActionHandler implements IActionHandler {

    /**
     * Empty method (for "no-op" actions used to just
     * refresh the display, for instance).
     *
     * @param action A LabyAction object created based on the client HTTP request.
     * @exception LabyException
     * @see com.jlaby.action.actions.NoAction
     */
    public void performActionAsSoonAsPossibleBeforeItsTooLate(LabyAction action) throws LabyException {
    }
}
