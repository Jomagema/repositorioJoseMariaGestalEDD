package com.jlaby.action.handlers;

/*
 * @(#)WalkActionHandler.java 0.1 99/Feb/12
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

/**
 * This class handles walking actions of a character.
 * It implements walking by moving the character 1 field
 * forward (in its viewing direction).
 *
 * @author  Marcel Schoen
 * @version $Id: WalkActionHandler.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class WalkActionHandler implements IActionHandler {

    // Movement mappings
    private static int[] ms_forwardX = { -99999, 0, 0, 1, -1};
    private static int[] ms_forwardY = { -99999, -1, 1, 0, 0};

    /**
     * Checks if the game character can walk (i.e. if there
     * is nothing standing in its way) and if so, changes its
     * coordinates accordingly.
     *
     * @param action A WalkAction object created based on the client HTTP request.
     * @exception LabyException
     * @see com.jlaby.action.actions.WalkAction
     */
    public void performActionAsSoonAsPossibleBeforeItsTooLate(LabyAction action) throws LabyException {
        GameCharacter character = (GameCharacter)action.getSource();
        int direction = character.getDirection();
        int x = character.getX() + ms_forwardX[direction];
        int y = character.getY() + ms_forwardY[direction];
        character.setX(x);
        character.setY(y);
    }

}
