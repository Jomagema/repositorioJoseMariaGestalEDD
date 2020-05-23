package com.jlaby.action.actions;

/*
 * @(#)TurnAction.java 0.1 99/Feb/12
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
 * Action for character turning around
 *
 * @author  Marcel Schoen
 * @version $Id: TurnAction.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class TurnAction extends LabyAction {

    // **************************************************
    // CONSTANTS
    // **************************************************
    public final static int AROUND = 0;  // 180 degree
    public final static int RIGHT = 1;
    public final static int LEFT = 2;

    // **************************************************
    // VARIABLES
    // **************************************************
    private int m_side = 0;

    /**
     * Constructor used by the client to create
     * action objects.
     */
    public TurnAction(int side) {
        m_side = side;
    }

    public final int getSide() {
        return m_side;
    }

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
        return "com.jlaby.action.handlers.TurnActionHandler";
    }
}
