package com.jlaby.action.handlers;

/*
 * @(#)TurnActionHandler.java 0.1 99/Feb/12
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
 * This class handles a turning action of a character.
 * It implements turning to the left or right (90 degree)
 * or for 180 degree.
 *
 * @author  Marcel Schoen
 * @version $Id: TurnActionHandler.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class TurnActionHandler implements IActionHandler {

    private static int[] ms_nextDirRight = {-9999, 3, 4, 2, 1};
    private static int[] ms_nextDirLeft = {-9999, 4, 3, 1, 2};

    /**
     * Carries out some action(s) based on the action object
     * and the clients request.
     *
     * @param action A LabyAction object created based on the client HTTP request.
     * @exception LabyException
     * @see com.jlaby.action.actions.TurnAction
     */
    public void performActionAsSoonAsPossibleBeforeItsTooLate(LabyAction action) throws LabyException {
        TurnAction turnAction = (TurnAction)action;
        GameCharacter character = (GameCharacter)action.getSource();
        int side = turnAction.getSide();
        if(side == TurnAction.LEFT) {
            int direction = character.getDirection();
            character.setDirection(ms_nextDirLeft[direction]);
        }
        if(side == TurnAction.RIGHT) {
            int direction = character.getDirection();
            character.setDirection(ms_nextDirRight[direction]);
        }
    }

}
