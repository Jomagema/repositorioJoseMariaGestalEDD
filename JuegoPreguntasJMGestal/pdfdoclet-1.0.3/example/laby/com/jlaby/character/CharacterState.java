package com.jlaby.character;

/*
 * @(#)CharacterState.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.exception.*;
import com.jlaby.jdbc.*;
import com.jlaby.jdbc.exception.*;
import com.jlaby.log.*;

/**
 * Helper class which handles state persistency
 * of GameCharacter objects.
 *
 * @author  Marcel Schoen
 * @version $Id: CharacterState.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 */
public class CharacterState  {

    /**
     * Load the GameCharacter from the database.
     *
     * @param name The (unique) name of the game character.
     * @return A GameCharacter object holding all information.
     * @exception UnknownUserException If no entry with the given name could be found in the database.
     */
    public static GameCharacter load(String name) throws UnknownUserException {
        // Load user state from database. This method throws a
        // UserNotFoundException if the user is not in the database.
        Log.info("CharacterState", "load", "Load data of character: "+name);
        try {
            GameCharacter character = JdbcUtil.loadCharacter(name);
            character.setStateful(true);
            Log.info("CharacterState", "load", "Character data loaded.");
            return character;
        } catch(NoRecordFoundException e) {
            throw new UnknownUserException("Character name: "+name);
        }
    }

    /**
     * Save the GameCharacter into the database.
     *
     * @param character The game character that should be saved.
     * @exception LabySQLException thrown in case of database problems.
     */
    public static void save(GameCharacter character) throws LabySQLException {
        // Save user state into database.
        try {
            Log.info("CharacterState", "save", "Save state in DB...");
            JdbcUtil.saveCharacter(character);
        } catch(NoRecordUpdatedException e) {
            throw new LabySQLException("User data not stored: "+character);
        }
    }
}

