package com.test;

/*
 * @(#)ITestInterface.java 0.1 99/Feb/12
 *
 */

/**
 * Simple interface for pdfdoclet testing.
 *
 * @author  Marcel Schoen
 * @version $Id: ITestInterface.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 * @deprecated use the new {@linkplain com.test.ISecondInterface interface} for now
 */
public interface ITestInterface {

    // **************************************************
    // CONSTANTS
    // **************************************************

    // Directions
    
    /** Constant value for northern direction. */
    public static final int NORTH = 1;
    
    /** Constant value for southern direction. */
    public static final int SOUTH = 2;
    
    /** Constant value for eastern direction. */
    public static final int EAST = 3;
    
    /** Constant value for western direction. */
    public static final int WEST = 4;

    /**
     * Initializes the world (sounds nice, doesn't it?).
     * In other words, it does in a few milliseconds what
     * took god six days... so to speak...
     *
     * @deprecated
     * @throws RuntimeException if something went wrong and the
     *                                       world could not be created.
     */
    public void initialize() throws RuntimeException;

    /**
     * This method has to deal with an action created by
     * a "client" (local or remote). It has to have the
     * action handled by the appropriate handler and then
     * create an answer to be returned to the client.
     *
     * @param action the action created /sent by a client.
     * @return the answer for the client.
     * @exception LabyException thrown if the action could not be handled.
     */
    public Object handleAction(Object action) throws Exception;
}
