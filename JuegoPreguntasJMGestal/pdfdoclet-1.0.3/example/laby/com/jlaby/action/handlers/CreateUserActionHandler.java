package com.jlaby.action.handlers;

/*
 * @(#)CreateUserActionHandler.java 0.1 99/Feb/12
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
import com.jlaby.jdbc.exception.*;
import com.jlaby.jdbc.*;

/**
 * This class handles the action of creating a new
 * user (character).
 *
 * @author  Marcel Schoen
 * @version $Id: CreateUserActionHandler.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class CreateUserActionHandler implements IActionHandler {

    /**
     * Creates a new user entry in the Laby user database.
     *
     * @param action A CreateUserAction object created based on the client HTTP request.
     * @exception LabyException
     * @see com.jlaby.action.actions.CreateUserAction
     */
    public void performActionAsSoonAsPossibleBeforeItsTooLate(LabyAction action) throws LabyException {
        CreateUserAction loginAction = (CreateUserAction)action;
        String name = loginAction.getCharacterName();
        String password1 = loginAction.getPassword1();
        String password2 = loginAction.getPassword2();
        Log.info(this, "performAction", "create character: "+name);
        if(name != null) {
            try {
                JdbcUtil.createCharacter(name);
                // Now have the client to go to the login gui
                throw new UserNotLoggedInException("New user needs to log in.");
            } catch(NoRecordFoundException e) {
                throw new UnknownUserException("User: " + name);
            }
        }
    }
}
