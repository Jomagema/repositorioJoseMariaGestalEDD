package com.jlaby.world.exception;

/**
 * @(#)InitializationFailedException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */
import com.jlaby.exception.LabyException;

/**
 * This exception is thrown when a problem occurred
 * during the initialization of a newly created
 * Laby world object (remote or local).
 *
 * @version $Id: InitializationFailedException.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 * @author  Marcel Schoen
 */
public class InitializationFailedException extends WorldException {

    /**
     * Constructs an unspecific exception.
     */
    public InitializationFailedException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public InitializationFailedException(String txt) {
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
    public InitializationFailedException(Throwable causingException) {
        super(causingException);
    }
}
