package com.jlaby.client;

/*
 * @(#)GameCharacter.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.character.*;
import com.jlaby.view.*;
import com.jlaby.world.*;
import java.io.*;

/**
 * Contains the information about a game character
 * which should be available to this character. For
 * example, its absolute position within the Laby
 * should NOT be available to the character as long
 * as no GPS-like device has been picked up.
 *
 * @author  Marcel Schoen
 * @version $Id: GameCharacterInfo.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 */
public class GameCharacterInfo implements Serializable {

    // **************************************************
    // VARIABLES
    // **************************************************
    private String m_name = "";
    private String m_alias = "";
    private int m_direction = -1;
    private Position m_position = null;
    private int m_state = GameCharacter.SLEEPING;
    private int m_life = 100;
    private ViewObject[] m_viewObjects = null;

    public void setViewObjects(ViewObject[] viewObjects) {
        m_viewObjects = viewObjects;
    }

    public ViewObject[] getViewObjects() {
        return m_viewObjects;
    }

    public void setName(String name) {
        m_name = name;
    }

    public String getName() {
        return m_name;
    }

    public void setAlias(String alias) {
        m_alias = alias;
    }

    public String getAlias() {
        return m_alias;
    }

    public void setPosition(Position position) {
        m_position = position;
    }

    public Position getPosition() {
        return m_position;
    }

    public void setDirection(int value) {
        m_direction = value;
    }

    public int getDirection() {
        return m_direction;
    }

    public void setState(int value) {
        m_state = value;
    }

    public int getState() {
        return m_state;
    }

    public void setLife(int value) {
        m_life = value;
    }

    public int getLife() {
        return m_life;
    }
}

