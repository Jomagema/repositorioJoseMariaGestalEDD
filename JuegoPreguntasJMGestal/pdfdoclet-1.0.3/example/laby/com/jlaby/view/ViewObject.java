package com.jlaby.view;

/*
 * @(#)ViewObjects.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.util.*;
import java.io.*;

import com.jlaby.util.*;
import com.jlaby.world.*;

/**
 * Object in the laby.
 *
 * @author  Marcel Schoen
 * @version $Id: ViewObject.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class ViewObject implements Serializable, IRepresentationTypes {

    // **************************************************
    // VARIABLES
    // **************************************************
    private int m_direction = -1;
    private Position m_position = new Position();
    private int m_objectType = 0;

    /**
     * Constructs the
     */
    public ViewObject() {
    }

    public void setObjectType(int objectType) {
        m_objectType = objectType;
    }

    public int getObjectType() {
        return m_objectType;
    }

    public void setDirection(int direction) {
        m_direction = direction;
    }

    public void setPosition(Position position) {
        m_position = position;
    }

    public int getDirection() {
        return m_direction;
    }

    public Position getPosition() {
        return m_position;
    }
}
