package com.jlaby.exception;

/**
 * @(#)LabyException.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */

/**
 * The mother of all exceptions thrown in the
 * Laby software realms. It is derived from
 * java.lang.RuntimeException in order to make
 * exception handling easier. This way, method
 * signatures can stay lean and simple, and
 * the exceptions can be handled at the
 * appropriate level.
 * <P>
 * If you don't like this approach: Go f... yourself!
 *
 * @version $Id: LabyException.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 * @author  Marcel Schoen
 */

public class LabyException extends java.lang.RuntimeException {

    private Throwable m_causingException = null;

    /**
     * Constructs an unspecific exception.
     */
    public LabyException() {
        super();
    }

    /**
     * Constructs an exception with a message text.
     *
     * @param txt the exception message text.
     */
    public LabyException(String txt) {
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
    public LabyException(Throwable causingException) {
        m_causingException = causingException;
    }

    /**
     * Returns a reference to the wrapped exception which
     * originally caused the problem.
     *
     * @return the wrapped exception object.
     */
    public Throwable getCausingException() {
        return m_causingException;
    }
}
