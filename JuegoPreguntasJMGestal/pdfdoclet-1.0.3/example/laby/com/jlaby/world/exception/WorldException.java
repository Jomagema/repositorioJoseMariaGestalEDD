package com.jlaby.world.exception;

/**
 * @(#)WorldException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */
import com.jlaby.exception.LabyException;

/**
 * Generic world-related exception.
 *
 * @version $Id: WorldException.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 * @author  Marcel Schoen
 */

public class WorldException extends LabyException {

    /**
     * Constructs an unspecific exception.
     */
    public WorldException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public WorldException(String txt) {
        super(txt);
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
    public WorldException(Throwable causingException) {
        super(causingException);
    }
}
