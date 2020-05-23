package com.jlaby.jdbc.exception;

/**
 * @(#)LabySQLException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */
import com.jlaby.exception.LabyException;

/**
 * Exception for db related problems.
 *
 * @version $Id: LabySQLException.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 * @author  Marcel Schoen
 */

public class LabySQLException extends LabyException {

    /**
     * Constructs an unspecific exception.
     */
    public LabySQLException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public LabySQLException(String msg) {
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
    public LabySQLException(Exception e) {
        super(e);
    }
}
