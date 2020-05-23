package com.jlaby.log;

/*
 * @(#)Log.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;

import com.ibm.logging.MessageLogger;
import com.ibm.logging.TraceLogger;
import com.ibm.logging.IRecordType;

/**
 *
 * @author Renator Steiner
 * @version $Id: Log.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class Log {

    /**
     * Reference to a message logger object (used to log info, warning, error and severe log records
     */
    private static MessageLogger ms_msgLogger = null;

    /**
     * Reference to a trace logger object (used to log trace log records; method entry/exit log records)
     */
    private static TraceLogger ms_traceLogger = null;

    /**
     * LogManager is a manager of logging objects
     */
    private static LogManager ms_logManager = null;

    public static void initialize() {
        ms_logManager = LogManager.instance();
        ms_msgLogger = ms_logManager.getMessageLogger();
        ms_traceLogger = ms_logManager.getTraceLogger();
    }

    /**
     * Log an info message with a free text.
     *
     * @param objref The reference to the object that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the info message.
     */
    public final static void info(Object objref, String methodName, String text) {
        ms_msgLogger.text( IRecordType.TYPE_INFO, objref, methodName, text );
    }

    /**
     * Log an info message with a free text.
     *
     * @param className The name of the class that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the info message.
     */
    public final static void info(String className, String methodName, String text) {
        ms_msgLogger.text( IRecordType.TYPE_INFO, className, methodName, text );
    }

    /**
     * Log a warning message with a free text.
     *
     * @param objref The reference to the object that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the warning message.
     */
    public final static void warning(Object objref, String methodName, String text) {
        ms_msgLogger.text( IRecordType.TYPE_WARNING, objref, methodName, text );
    }

    /**
     * Log a warning message with a free text.
     *
     * @param className The name of the class that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the warning message.
     */
    public final static void warning(String className, String methodName, String text) {
        ms_msgLogger.text( IRecordType.TYPE_WARNING, className, methodName, text );
    }

    /**
     * Log an error message with a free text.
     *
     * @param objref The reference to the object that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the error message.
     */
    public final static void error(Object objref, String methodName, String text) {
        ms_msgLogger.text( IRecordType.TYPE_ERROR, objref, methodName, text );
    }

    /**
     * Log an error message with a free text.
     *
     * @param className The name of the class that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the error message.
     */
    public final static void error(String className, String methodName, String text) {
        ms_msgLogger.text( IRecordType.TYPE_ERROR, className, methodName, text );
    }

    /**
     * Log a severe error message with a free text.
     *
     * @param objref The reference to the object that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the severe message.
     */
    public final static void severe(Object objref, String methodName, String text) {
        ms_msgLogger.text( IRecordType.TYPE_FATAL, objref, methodName, text );
    }

    /**
     * Log a severe error message with a free text.
     *
     * @param className The name of the class that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the severe message.
     */
    public final static void severe(String className, String methodName, String text) {
        ms_msgLogger.text( IRecordType.TYPE_FATAL, className, methodName, text );
    }

    /**
     * Log a trace message with a free text.
     *
     * @param objref The reference to the object that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the trace message.
     */
    public final static void trace(Object objref, String methodName, String text) {
        ms_traceLogger.text( IRecordType.TYPE_DEFAULT_TRACE, objref, methodName, text );
        //logger.text( IRecordType.TYPE_INFO, objref, methodName, text );
    }

    /**
     * Log a trace error message with a free text.
     *
     * @param className The name of the class that created the message.
     * @param methodName The method where the message originated.
     * @param text The text of the trace message.
     */
    public final static void trace(String className, String methodName, String text) {
        ms_traceLogger.text( IRecordType.TYPE_DEFAULT_TRACE, className, methodName, text );
        //logger.text( IRecordType.TYPE_INFO, className, methodName, text );
    }

    /**
     * Log a trace message with a free text and parameters. This
     * method shall be called when a method has been entered.
     *
     * @param className The name of the class that created the message.
     * @param methodName The method where the message originated.
     * @param params  An array of elements to be inserted into the message
     */
    public final static void traceEntry(String className, String methodName, Object[] params) {
        ms_traceLogger.entry( IRecordType.TYPE_ENTRY, className, methodName, params );
    }

    /**
     * Log a trace message with a free text and parameters. This
     * method shall be called when a method has been entered.
     *
     * @param objRef The reference to the object that created the message.
     * @param methodName The method where the message originated.
     * @param params  An array of elements to be inserted into the message
     */
    public final static void traceEntry(Object objref, String methodName, Object[] params) {
        ms_traceLogger.entry( IRecordType.TYPE_ENTRY, objref, methodName, params );
    }

    /**
     * Log a trace message with a free text and parameters. This
     * method shall be called when a method has been entered.
     *
     * @param className The name of the class that created the message.
     * @param methodName The method where the message originated.
     */
    public static void traceEntry(String className, String methodName) {
        ms_traceLogger.entry( IRecordType.TYPE_ENTRY, className, methodName);
    }

    /**
     * Log a trace message with a free text and parameters. This
     * method shall be called when a method has been entered.
     *
     * @param objref The reference to the object that created the message.
     * @param methodName The method where the message originated.
     */
    public static void traceEntry(Object objref, String methodName) {
        ms_traceLogger.entry( IRecordType.TYPE_ENTRY, objref, methodName);
    }

    /**
     * Log a trace message with a free text and parameters. This
     * method shall be called before a method is exited.
     *
     * @param className The name of the class that created the message.
     * @param methodName The method where the message originated.
     * @param rc The return code of the method
     */
    public final static void traceExit(String className, String methodName, long rc ) {
        ms_traceLogger.exit( IRecordType.TYPE_EXIT, className, methodName, rc );
    }

    /**
     * Log a trace message with a free text and parameters. This
     * method shall be called before a method is exited.
     *
     * @param objref The reference to the object that created the message.
     * @param methodName The method where the message originated.
     * @param rc The return code of the method
     */
    public final static void traceExit(Object objref, String methodName, long rc) {
        ms_traceLogger.exit( IRecordType.TYPE_EXIT, objref, methodName, rc );
    }

    /**
     * Returns true when logging is active
     *
     * @return true if logging is active
     */
    public static final boolean isLoggingActive() {
        return( ms_msgLogger.isLogging );
    }

    /**
     * Returns true when traceing is active
     *
     * @return true if traceing is active
     */
    public final static boolean isTraceActive() {
        return( ms_traceLogger.isLogging );
    }
}
