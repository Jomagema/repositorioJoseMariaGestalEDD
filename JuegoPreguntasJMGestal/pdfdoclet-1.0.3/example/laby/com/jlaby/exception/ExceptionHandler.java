package com.jlaby.exception;

/*
 * @(#)ExceptionHandler.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import com.jlaby.exception.*;
import com.jlaby.log.*;
import com.jlaby.util.*;

/**
 * Generic handler for exceptions, used to provide
 * a consistent and centralized handling of all
 * uncaught exceptions.
 *
 * @author  Marcel Schoen
 * @version $Id: ExceptionHandler.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class ExceptionHandler {

    /**
     * This method takes an exception and carries
     * out the following steps:
     * <ul>
     * <li>create a HTML error page if an appropriate servlet
     * output stream can be found.</li>
     * <li>print the stack trace in <code>System.out</code></li>
     * <li>log the exception in the Laby log file.</li>
     * </ul>
     *
     * @param e the exception that should be handled.
     */
    public static void handleException(Throwable e) {
        PrintWriter out = Util.getOut();
        if(out != null) {
            out.println("<H3>ERROR:</H3><P><PRE>");
            out.println("Exception: "+exceptionInfo(e));
            e.printStackTrace(out);
            out.println("</PRE>");
        } else {
            e.printStackTrace();
        }
        logException(e);

        if(e instanceof LabyException) {
            LabyException ex = (LabyException)e;
            Throwable causing = ex.getCausingException();
            if(out != null) {
                out.println("<H4>Causing exception: "+exceptionInfo(causing)+"</H4><PRE>");
                causing.printStackTrace(out);
                out.println("</PRE>");
            } else {
                causing.printStackTrace();
            }
            logException(causing);
        }
        if(out != null) {
            out.flush();
        }
    }

    /**
     * Utility method which turns the stack trace of an exception
     * into an array of Strings in order to write the stack trace
     * into the log file.
     *
     * @param e the exception of which the stack trace should be logged.
     */
    private static void logException(Throwable e) {
        StackTraceCollector collector = new StackTraceCollector(e);
        String[] stackTrace = collector.getStackTrace();
        Log.error("ExceptionHandler", "handleException", "Exception: "+exceptionInfo(e));
        for(int i=0; i<stackTrace.length; i++) {
            Log.error("ExceptionHandler", "handleException", "    "+stackTrace[i]);
        }
        Log.error("ExceptionHandler", "handleException", "    ");
    }

    /**
     * Utility method which shows the class name and message of
     * a given exception in a more convenient for usage in
     * error messages etc:<P>
     * <code>classname(message)</code>
     *
     * @param e the exception
     * @return the String with the simplified message text.
     */
    private static String exceptionInfo(Throwable e) {
        String result = e.getClass().getName()+"("+e.getMessage()+")";
        return result;
    }
}
