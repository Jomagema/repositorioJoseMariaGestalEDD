package com.jlaby.action.handlers;

/*
 * @(#)LoginActionHandler.java 0.1 99/Feb/12
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
import com.jlaby.util.*;

import javax.servlet.http.*;

/**
 * This class handles a login action of a character.
 *
 * @author  Marcel Schoen
 * @version $Id: LoginActionHandler.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class LoginActionHandler implements IActionHandler {

    /**
     * Executes the log in procedure for a user
     * and sets the ID of the user in the HTTP
     * session.
     *
     * @param action A LabyAction object created based on the client HTTP request.
     * @exception LabyException
     * @see com.jlaby.action.actions.LoginAction
     */
    public void performActionAsSoonAsPossibleBeforeItsTooLate(LabyAction action) throws LabyException {

        LoginAction loginAction = (LoginAction)action;
        String name = loginAction.getCharacterName();
        String password = loginAction.getPassword();
        Log.info(this, "performAction", "login: "+name+", password: "+password);
        verifyPassword(password);

        // Since logging in is a public action, the current source
        // (originator) character of the action is an anonymous dummy
        // character. Replace it with the real character.
        action.setSource(CharacterState.load(name));

        HttpSession session = Util.getSession();
        session.setAttribute("LabyID", name);
    }

    /**
     * This method verifies the password through a lookup
     * in the database (not implemented yet).
     *
     * @param password the users login password.
     * @exception InvalidPasswordException thrown if the password could
     *                                     not be verified successfully.
     */
    private void verifyPassword(String password) throws InvalidPasswordException {
        if(password.toLowerCase().equals("fail")) {
            throw new InvalidPasswordException();
        }
    }
}
