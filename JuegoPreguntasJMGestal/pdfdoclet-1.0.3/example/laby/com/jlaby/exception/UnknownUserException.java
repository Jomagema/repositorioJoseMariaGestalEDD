package com.jlaby.exception;

/**
 * @(#)UnknownUserException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */

/**
 * This exception is thrown when the name presented
 * by a user was unknown (could not be found in the db)
 *
 * @version $Id: UnknownUserException.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 * @author  Marcel Schoen
 */
public class UnknownUserException extends LabyException {

    /**
     * Constructs an unspecific exception.
     */
    public UnknownUserException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public UnknownUserException(String txt) {
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
    public UnknownUserException(Throwable causingException) {
        super(causingException);
    }
}
