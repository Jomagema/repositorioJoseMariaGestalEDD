package com.jlaby.client.exception;

/**
 * @(#)RemoteException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */
import com.jlaby.exception.LabyException;

/**
 * This exception is thrown when a problem occurred
 * during the creation or transmission of a HTTP POST request.
 *
 * @version $Id: RemoteException.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 * @author  Marcel Schoen
 */
public class RemoteException extends LabyException {

    /**
     * Constructs an unspecific exception.
     */
    public RemoteException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public RemoteException(String txt) {
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
    public RemoteException(Throwable causingException) {
        super(causingException);
    }
}
