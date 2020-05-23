package com.jlaby.client.exception;

/**
 * @(#)UnsupportedAnswerException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */
import com.jlaby.exception.LabyException;

/**
 * Exception thrown when an answer sent from the
 * server cannot be handled by the client.
 *
 * @version $Id: UnsupportedAnswerException.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 * @author  Marcel Schoen
 */

public class UnsupportedAnswerException extends LabyException {

    /**
     * Constructs an unspecific exception.
     */
    public UnsupportedAnswerException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public UnsupportedAnswerException(String msg) {
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
    public UnsupportedAnswerException(Exception e) {
        super(e);
    }
}
