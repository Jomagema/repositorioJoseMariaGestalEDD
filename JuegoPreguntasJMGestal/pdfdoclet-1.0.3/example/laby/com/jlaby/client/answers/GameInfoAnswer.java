package com.jlaby.client.answers;

/*
 * @(#)GameInfoAnswer.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.util.*;

import com.jlaby.client.*;

/**
 * Answer for an authenticated and active client which informs
 * informs him about the current state of the character etc.
 *
 * @author  Marcel Schoen
 * @version $Id: GameInfoAnswer.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class GameInfoAnswer extends LabyAnswer {

    // **************************************************
    // VARIABLES
    // **************************************************
    private GameCharacterInfo m_characterInfo = null;

    /**
     * The detail type of the login related answer.
     *
     * @param detailType must be one of the constants.
     */
    public GameInfoAnswer(GameCharacterInfo characterInfo) {
        super(LabyAnswer.GAME_RELATED);
        m_characterInfo = characterInfo;
    }

    /**
     * Returns information about the game character.
     *
     * @return a GameCharacterInfo object which contains filtered
     *         information (it contains all the information the
     *         GameCharacter is allowed to know).
     */
    public GameCharacterInfo getCharacterInfo() {
        return m_characterInfo;
    }

}
