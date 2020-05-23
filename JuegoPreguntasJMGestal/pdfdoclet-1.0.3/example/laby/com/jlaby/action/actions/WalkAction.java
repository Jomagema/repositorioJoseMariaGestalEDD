package com.jlaby.action.actions;

/*
 * @(#)WalkAction.java 0.1 99/Feb/12
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
 * Walking action (walk one step forward)
 *
 * @author  Marcel Schoen
 * @version $Id: WalkAction.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class WalkAction extends LabyAction {

    /**
     * Returns the type of the action (authenticated or public).
     *
     * @return The type of the action.
     */
    public int getType() {
        return LabyAction.AUTHENTICATED_ACTION;
    }

    /**
     * Returns the name of the handler for the action.
     *
     * @returns The class name of the handler for this action.
     */
    public String getHandler() {
        return "com.jlaby.action.handlers.WalkActionHandler";
    }
}
