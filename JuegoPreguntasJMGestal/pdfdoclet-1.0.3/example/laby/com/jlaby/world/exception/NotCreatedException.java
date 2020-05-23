package com.jlaby.world.exception;

/**
 * @(#)NotCreatedException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */
import com.jlaby.exception.LabyException;

/**
 * This exception is thrown when an attempt was made
 * to access the static, singleton instance of the world
 * object before it had actually been created.
 *
 * @version $Id: NotCreatedException.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 * @author  Marcel Schoen
 */
public class NotCreatedException extends WorldException {

    /**
     * Constructs an unspecific exception.
     */
    public NotCreatedException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public NotCreatedException(String txt) {
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
    public NotCreatedException(Throwable causingException) {
        super(causingException);
    }
}
