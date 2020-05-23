package com.jlaby.view;

/*
 * @(#)IRepresentationTypes.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */

/**
 * Constants for types of objects (or, to be
 * more specific, graphical representations of
 * objects, be it characters, items or whatever).
 *
 * @author  Marcel Schoen
 * @version $Id: IRepresentationTypes.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public interface IRepresentationTypes {

    // **************************************************
    // CONSTANTS FOR TYPES OF OBJECTS
    // **************************************************
    public final static int WALL = 1;
    public final static int CHARACTER = 2;
    public final static int TREE = 3;
    public final static int COLUMN = 4;

    // **************************************************
    // CONSTANTS FOR DIRECTION WHICH OBJECT FACES
    // **************************************************
    public final static int UNDEFINED = -1;
    public final static int LOOKING_AWAY = 1;
    public final static int FACING_PLAYER = 2;
    public final static int LOOKING_RIGHT = 3;
    public final static int LOOKING_LEFT = 4;
}
