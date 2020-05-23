package com.jlaby.world;

/*
 * @(#)Position.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;

/**
 * This class is a holder for information about the
 * position of any object within the Laby world.
 *
 * @author  Marcel Schoen
 * @version $Id: Position.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class Position implements Serializable {

    private int m_x = 0;
    private int m_y = 0;
    private int m_z = 0;

    /**
     * Default constructor.
     */
    public Position() {
        m_x = 0;
        m_y = 0;
        m_z = 0;
    }

    /**
     * Constructs a position object.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    public Position(int x, int y, int z) {
        m_x = x;
        m_y = y;
        m_z = z;
    }

    /**
     * Sets the x-coordinate.
     *
     * @param value the value for the x-coordinate.
     */
    public void setX(int value) {
        m_x = value;
    }

    /**
     * Sets the y-coordinate.
     *
     * @param value the value for the y-coordinate.
     */
    public void setY(int value) {
        m_y = value;
    }

    /**
     * Sets the z-coordinate.
     *
     * @param value the value for the z-coordinate.
     */
    public void setZ(int value) {
        m_z = value;
    }

    /**
     * Returns the x-coordinate.
     *
     * @return the value of the x-coordinate.
     */
    public int getX() {
        return m_x;
    }

    /**
     * Returns the y-coordinate.
     *
     * @return the value of the y-coordinate.
     */
    public int getY() {
        return m_y;
    }

    /**
     * Returns the z-coordinate.
     *
     * @return the value of the z-coordinate.
     */
    public int getZ() {
        return m_z;
    }
}
