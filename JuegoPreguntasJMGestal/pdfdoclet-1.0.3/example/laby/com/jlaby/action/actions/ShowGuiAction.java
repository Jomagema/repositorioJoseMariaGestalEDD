package com.jlaby.action.actions;

/*
 * @(#)ShowGuiAction.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.character.*;
import com.jlaby.action.*;

import java.util.*;

/**
 * Action for showing a specific GUI (HTML page)
 * to the user, such as a login etc.
 *
 * @author  Marcel Schoen
 * @version $Id: ShowGuiAction.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class ShowGuiAction extends LabyAction {

    // **************************************************
    // CONSTANTS
    // **************************************************
    public final static int LOGIN = 0;
    public final static int CREATEUSER = 1;

    // **************************************************
    // VARIABLES
    // **************************************************
    private int m_gui = 0;

    /**
     * Constructor used by the client to create
     * action objects.
     */
    public ShowGuiAction(int gui) {
        m_gui = gui;
    }

    public final int getGui() {
        return m_gui;
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
        return "com.jlaby.action.handlers.ShowGuiActionHandler";
    }
}
