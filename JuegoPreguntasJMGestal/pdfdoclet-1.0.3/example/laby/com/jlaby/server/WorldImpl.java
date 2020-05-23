package com.jlaby.server;

/*
 * @(#)WorldImpl.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.util.*;
import javax.servlet.http.*;

import com.jlaby.action.*;
import com.jlaby.action.handlers.*;
import com.jlaby.character.*;
import com.jlaby.client.*;
import com.jlaby.client.answers.*;
import com.jlaby.exception.*;
import com.jlaby.jdbc.*;
import com.jlaby.jdbc.exception.*;
import com.jlaby.log.*;
import com.jlaby.world.*;
import com.jlaby.world.exception.*;
import com.jlaby.util.*;
import com.jlaby.view.*;

/**
 * This class loads and holds the world data.
 *
 * @author  Marcel Schoen
 * @version $Id: WorldImpl.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class WorldImpl implements IWorld {

    // **************************************************
    // CONSTANTS
    // **************************************************

    private static int ms_startX = 0;
    private static int ms_startY = 0;

    private static boolean ms_initialized = false;
    private static char[][] ms_worldFields = null;

    /**
     * This table is used to determine the viewing
     * direction of a visible object (relative to
     * the viewing direction of the active character
     */
    private static int[][] ms_viewDirections = new int[5][5];

    // This hashtable stores active character objects
    private static Hashtable ms_characters = new Hashtable();

    // **************************************************
    // VARIABLES
    // **************************************************

    /**
     * Constructs the
     */
    public WorldImpl() {
        ms_viewDirections[IWorld.NORTH][IWorld.NORTH] = ViewObject.LOOKING_AWAY;  // both looking in same direction
        ms_viewDirections[IWorld.NORTH][IWorld.SOUTH] = ViewObject.FACING_PLAYER; // both looking at each other
        ms_viewDirections[IWorld.NORTH][IWorld.EAST] = ViewObject.LOOKING_RIGHT;
        ms_viewDirections[IWorld.NORTH][IWorld.WEST] = ViewObject.LOOKING_LEFT;

        ms_viewDirections[IWorld.SOUTH][IWorld.SOUTH] = ViewObject.LOOKING_AWAY;  // both looking in same direction
        ms_viewDirections[IWorld.SOUTH][IWorld.NORTH] = ViewObject.FACING_PLAYER; // both looking at each other
        ms_viewDirections[IWorld.SOUTH][IWorld.WEST] = ViewObject.LOOKING_RIGHT;
        ms_viewDirections[IWorld.SOUTH][IWorld.EAST] = ViewObject.LOOKING_LEFT;

        ms_viewDirections[IWorld.EAST][IWorld.SOUTH] = ViewObject.LOOKING_RIGHT;
        ms_viewDirections[IWorld.EAST][IWorld.NORTH] = ViewObject.LOOKING_LEFT;
        ms_viewDirections[IWorld.EAST][IWorld.WEST] = ViewObject.FACING_PLAYER;
        ms_viewDirections[IWorld.EAST][IWorld.EAST] = ViewObject.LOOKING_AWAY;

        ms_viewDirections[IWorld.WEST][IWorld.SOUTH] = ViewObject.LOOKING_LEFT;
        ms_viewDirections[IWorld.WEST][IWorld.NORTH] = ViewObject.LOOKING_RIGHT;
        ms_viewDirections[IWorld.WEST][IWorld.WEST] = ViewObject.LOOKING_AWAY;
        ms_viewDirections[IWorld.WEST][IWorld.EAST] = ViewObject.FACING_PLAYER;
    }

    public void initialize() throws InitializationFailedException {
        if(!ms_initialized) {
            String[] worldFields = new String[18];
            worldFields[0] = "WWWWWWWWWWWWWWWWWWWW";
            worldFields[1] = "W          WWW     W";
            worldFields[2] = "W          W WWWWWWW";
            worldFields[3] = "W          W WW WWWW";
            worldFields[4] = "W          W       W";
            worldFields[5] = "W          W WW WWWW";
            worldFields[6] = "W          W WWWWWWW";
            worldFields[7] = "W      TTTWW WWWW WW";
            worldFields[8] = "W                  W";
            worldFields[9] = "W      TTTWW WWWW WW";
            worldFields[10]= "WWWC CWWWWWWWWWWWWWW";
            worldFields[11]= "W                  W";
            worldFields[12]= "W WW WWW WWW WWW WWW";
            worldFields[13]= "W WW WWW WWW WW   WW";
            worldFields[14]= "W WW WWW WW   W   WW";
            worldFields[15]= "W WW WW   W   W   WW";
            worldFields[16]= "W W   W   W   W   WW";
            worldFields[17]= "WWWWWWWWWWWWWWWWWWWW";

            ms_worldFields = new char[worldFields[0].length()][worldFields.length];
            for(int y=0; y<worldFields.length; y++) {
                for(int x=0; x<worldFields[y].length(); x++) {
                    String singleChar = worldFields[y].substring(x, x+1);
                    ms_worldFields[x][y] = singleChar.charAt(0);
                    if(ms_worldFields[x][y] == 'P') {
                        ms_startX = x;
                        ms_startY = y;
                        ms_worldFields[x][y] = ' ';
                    }
                }
            }
            ms_initialized = true;
        }
    }

    /**
     * This method deals with an action created by
     * a "client" (local or remote). It creates the
     * appropriate handler for the action, has it
     * handled and returns the answer for the client.
     *
     * @param action the action created /sent by a client.
     * @return the answer for the client.
     * @exception LabyException thrown if the action could not be handled.
     */
    public LabyAnswer handleAction(LabyAction action) throws LabyException {
        LabyAnswer answer = null;
        if(action != null) {
            Log.info(this, "handleAction", "Get handler for action");
            IActionHandler handler = ActionHandlerFactory.getActionHandler(action);
            Log.info(this, "handleAction", "Let handler perform action: "+handler.getClass().getName());
            handler.performAction(action);

            // read the eventually changed character from the action
            GameCharacter character = action.getSource();

            // ******************************************
            // Save character state in DB (but only if
            // it wasn't a dummy for a public request)
            // ******************************************
            if(character.isStateful()) {
                Log.info(this, "handleAction", "Save character state");
                CharacterState.save(character);
            }

            // ******************************************
            // Create answer
            // ******************************************
            answer = new GameInfoAnswer(getCharacterInfo(character));

        }
        return answer;
    }

    /**
     * This method serves as a filter; it decides what information
     * about the GameCharacter itself and its environment will be
     * made available to it. For example, the absolute position of
     * the character within the Laby world is NOT available to it
     * as long as it has not picked up a GPS-like device.
     *
     * @param character the game character for which to create the information.
     * @returns the filtered information about the character.
     */
    private GameCharacterInfo getCharacterInfo(GameCharacter character) {
        GameCharacterInfo info = new GameCharacterInfo();

        // The following information is always
        // available to the character
        info.setViewObjects(getViewObjects(character));
        info.setLife(character.getLife());
        info.setState(character.getState());
        info.setName(character.getName());
        info.setAlias(character.getAlias());

        info.setDirection(character.getDirection());

        return info;
    }

    /**
     * Creates a list of objects visible to a character
     * (either human player or NPC) based on its position
     * and direction.
     */
    private ViewObject[] getViewObjects(GameCharacter character) {

        // variables defining the rectangle of the world scanned
        // for objects
        int leftX = character.getX();
        int leftY = character.getY();
        int rightX = character.getX();
        int rightY = character.getY();
        switch(character.getDirection()) {
        case IWorld.NORTH:
            leftY = leftY - 5;
            leftX = leftX - 2;
            rightX = rightX + 2;
            break;
        case IWorld.SOUTH:
            leftX = leftX - 2;
            rightX = rightX + 2;
            rightY = rightY + 5;
            break;
        case IWorld.EAST:
            leftY = leftY - 2;
            rightY = rightY + 2;
            rightX = rightX + 5;
            break;
        case IWorld.WEST:
            leftY = leftY - 2;
            rightY = rightY + 2;
            leftX = leftX - 5;
            break;
        }

        Vector viewObjectList = new Vector();

        // ******************************************************
        // Get Laby view objects first
        // ******************************************************
        int fieldX = 0, fieldY = 0, fieldNum = 0, counter = 0;
        for(int y=leftY; y<=rightY; y++) {
            for(int x=leftX; x<=rightX; x++) {
                if(y>-1 && y<ms_worldFields[0].length) {
                    if(x>-1 && x<ms_worldFields.length) {

                        if(ms_worldFields[x][y] != ' ') {
                            ViewObject obj = new ViewObject();
                            if(ms_worldFields[x][y] == 'W') {
                                obj.setObjectType(ViewObject.WALL);
                            }
                            if(ms_worldFields[x][y] == 'T') {
                                obj.setObjectType(ViewObject.TREE);
                            }
                            if(ms_worldFields[x][y] == 'C') {
                                obj.setObjectType(ViewObject.COLUMN);
                            }
                            obj.setDirection(ViewObject.FACING_PLAYER);

                            // Set position of view object relative to characters
                            // position. Note that the "z" coordinate (height) is
                            // always 0 because in the current laby world a character
                            // will always only see objects on the same height level.
                            obj.setPosition(new Position(x-character.getX(), y-character.getY(), 0));

                            viewObjectList.addElement(obj);
                        }
                    }
                }
                fieldX++;
                counter++;
            }
            fieldY++;
        }

        // ******************************************************
        // Then get other laby characters from db
        // ******************************************************
        GameCharacter[] characters = JdbcUtil.getCharacters(leftX, leftY, rightX, rightY);
        if(characters.length > 0) {
            for(int i=0; i<characters.length; i++) {
                ViewObject obj = new ViewObject();
                obj.setObjectType(ViewObject.CHARACTER);
                obj.setDirection(ms_viewDirections[character.getDirection()][characters[i].getDirection()]);

                obj.setPosition(new Position(characters[i].getX()-character.getX(),
                                characters[i].getY()-character.getY(),
                                0));
                viewObjectList.addElement(obj);
            }
        }

        ViewObject[] viewObjects = new ViewObject[viewObjectList.size()];
        for(int i=0; i<viewObjects.length; i++) {
            viewObjects[i] = (ViewObject)viewObjectList.elementAt(i);
        }

        return viewObjects;
    }
}
