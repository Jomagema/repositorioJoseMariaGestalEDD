package com.jlaby.character;

/*
 * @(#)GameCharacter.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */

/**
 * Object in the laby.
 *
 * @author  Marcel Schoen
 * @version $Id: GameCharacter.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 */
public class GameCharacter  {

    // **************************************************
    // CONSTANTS
    // **************************************************

    /**
     * Type values
     */
    public final static int PLAYER = 1;
    public final static int NON_PLAYER = 2;

    /**
     * State values
     */

    /**
     * Inactive user (not logged in)
     */
    public final static int SLEEPING = 0;

    /**
     * Active user (logged in)
     */
    public final static int AWAKE = 1;

    // **************************************************
    // VARIABLES
    // **************************************************
    private int m_id = 0;
    private String m_name = "";
    private String m_alias = "";
    private int m_direction = 1;
    private int m_xPosition = 1;
    private int m_yPosition = 1;
    private int m_zPosition = 1;
    private int m_state = SLEEPING;
    private int m_life = 100;
    private boolean m_isStateful = false;

    public String toString() {
        String txtID = " [ID]="+String.valueOf(m_id);
        String txtName = " [Name]='"+m_name+"'";
        String txtAlias = " [Alias]='"+m_alias+"'";
        String txtX = " [X]="+String.valueOf(m_xPosition);
        String txtY = " [Y]="+String.valueOf(m_yPosition);
        String txtZ = " [Z]="+String.valueOf(m_zPosition);
        String txtDirection = " [Direction]="+String.valueOf(m_direction);
        String txtState = " [State]="+String.valueOf(m_state);
        String txtLife = " [Life]="+String.valueOf(m_life);
        return (txtID + txtName + txtAlias + txtX + txtY + txtZ + txtDirection + txtState + txtLife);
    }

    public void setID(int id) {
        m_id = id;
    }

    public int getID() {
        return m_id;
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

    public boolean isStateful() {
        return m_isStateful;
    }

    public void setStateful(boolean value) {
        m_isStateful = value;
    }

    public void setX(int value) {
        m_xPosition = value;
    }

    public int getX() {
        return m_xPosition;
    }

    public void setY(int value) {
        m_yPosition = value;
    }

    public int getY() {
        return m_yPosition;
    }

    public void setZ(int value) {
        m_zPosition = value;
    }

    public int getZ() {
        return m_zPosition;
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

