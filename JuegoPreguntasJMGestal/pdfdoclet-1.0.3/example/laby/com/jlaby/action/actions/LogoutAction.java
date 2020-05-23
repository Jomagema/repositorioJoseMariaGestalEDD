package com.jlaby.action.actions;

/*
 * @(#)LogoutAction.java 0.1 99/Feb/12
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
 * Action for character logging out of the Laby.
 *
 * @author  Marcel Schoen
 * @version $Id: LogoutAction.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class LogoutAction extends LabyAction {

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
        return "com.jlaby.action.handlers.LogoutActionHandler";
    }
}
