package com.jlaby.action.handlers;

/*
 * @(#)LogoutActionHandler.java 0.1 99/Feb/12
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

import javax.servlet.http.*;
/**
 * This class handles a logout action of a character.
 *
 * @author  Marcel Schoen
 * @version $Id: LogoutActionHandler.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class LogoutActionHandler implements IActionHandler {

    /**
     * Carries out some action(s) based on the action object
     * and the clients request.
     *
     * @param action A LabyAction object created based on the client HTTP request.
     * @exception LabyException
     * @see com.jlaby.action.actions.LogoutAction
     */
    public void performActionAsSoonAsPossibleBeforeItsTooLate(LabyAction action) throws LabyException {
        Log.info(this, "performAction", "Logout character: "+(GameCharacter)action.getSource());
        throw new UserNotLoggedInException("User logged out.");
    }
}
