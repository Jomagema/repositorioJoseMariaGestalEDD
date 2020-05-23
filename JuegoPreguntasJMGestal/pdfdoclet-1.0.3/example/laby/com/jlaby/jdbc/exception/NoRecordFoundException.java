package com.jlaby.jdbc.exception;

/**
 * @(#)NoRecordFoundException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */
import com.jlaby.exception.LabyException;

/**
 * Thrown if a select statement returned zero records.
 *
 * @version $Id: NoRecordFoundException.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 * @author  Marcel Schoen
 */

public class NoRecordFoundException extends LabySQLException {

    /**
     * Constructs an unspecific exception.
     */
    public NoRecordFoundException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public NoRecordFoundException(String msg) {
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
    public NoRecordFoundException(Exception e) {
        super(e);
    }
}
