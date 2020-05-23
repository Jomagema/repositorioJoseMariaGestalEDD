package com.jlaby.action.handlers;

/*
 * @(#)ShowGuiActionHandler.java 0.1 99/Feb/12
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
 * This class handles gui display actions. These are
 * actually somewhat similar (logically) to a redirect;
 * instead of continuing the internal logic, the servlet
 * will be forced to send a "display gui xxx" answer to
 * the client by throwing the appropriate exception.
 *
 * @author  Marcel Schoen
 * @version $Id: ShowGuiActionHandler.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class ShowGuiActionHandler implements IActionHandler {

    /**
     * Carries out some action(s) based on the action object
     * and the clients request.
     *
     * @param action A LabyAction object created based on the client HTTP request.
     * @exception LabyException
     * @see com.jlaby.action.actions.ShowGuiAction
     */
    public void performActionAsSoonAsPossibleBeforeItsTooLate(LabyAction action) throws LabyException {
        ShowGuiAction showAction = (ShowGuiAction)action;
        if(showAction.getGui() == ShowGuiAction.CREATEUSER) {
            Log.info(this, "performAction", "Show create user gui...");
            throw new UnknownUserException();
        }
        if(showAction.getGui() == ShowGuiAction.LOGIN) {
            Log.info(this, "performAction", "Show login gui...");
            throw new UserNotLoggedInException();
        }
    }
}
