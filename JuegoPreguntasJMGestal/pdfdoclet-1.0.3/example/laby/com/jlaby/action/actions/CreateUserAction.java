package com.jlaby.action.actions;

/*
 * @(#)CreateUserAction.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.action.*;
import com.jlaby.character.*;

import java.util.*;

/**
 * Action for creating a new character (user)
 *
 * @author  Marcel Schoen
 * @version $Id: CreateUserAction.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class CreateUserAction extends LabyAction {

    // **************************************************
    // VARIABLES
    // **************************************************
    private String m_name = "";
    private String m_password1 = "";
    private String m_password2 = "";

    public CreateUserAction(String name, String password1, String password2) {
        m_name = name;
        m_password1 = password1;
        m_password2 = password2;
    }

    public String getCharacterName() {
        return m_name;
    }

    public String getPassword1() {
        return m_password1;
    }

    public String getPassword2() {
        return m_password2;
    }

    /**
     * Returns the type of the action (authenticated or public).
     *
     * @return The type of the action.
     */
    public int getType() {
        return LabyAction.PUBLIC_ACTION;
    }

    /**
     * Returns the name of the handler for the action.
     *
     * @returns The class name of the handler for this action.
     */
    public String getHandler() {
        return "com.jlaby.action.handlers.CreateUserActionHandler";
    }
}
