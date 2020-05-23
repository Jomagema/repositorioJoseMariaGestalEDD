package com.jlaby.action.exception;

/**
 * @(#)UnsupportedActionException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */
import com.jlaby.exception.LabyException;

/**
 * Exception for cases where the client sent an
 * Action object for which no appropriate handler
 * exists (although this should not be possible
 * at all as of now - maybe in future versions
 * the whole action object stuff will become
 * completely dynamic).
 *
 * @version $Id: UnsupportedActionException.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 * @author  Marcel Schoen
 */

public class UnsupportedActionException extends LabyException {

    /**
     * Constructs an unspecific exception.
     */
    public UnsupportedActionException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public UnsupportedActionException(String msg) {
        super(msg);
    }

    /**
     * Constructs an exception with a reference
     * to another exception which caused the problem.
     * The causing exception will be wrapped into this
     * exception and can be retrieved later using
     * the "getCausingException()" method.
     *
     * @param causingException the problem-causing exception.
     */
    public UnsupportedActionException(Exception e) {
        super(e);
    }
}
