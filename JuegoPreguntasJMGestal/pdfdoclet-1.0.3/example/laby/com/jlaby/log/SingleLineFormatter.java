package com.jlaby.log;

/*
 * @(#)SingleLineFormatter.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.logging.Formatter;
import com.ibm.logging.ILogRecord;
import com.ibm.logging.IRecordType;

/**
 * SingleLineFormatter is used to output a single log-entry containing the following
 * format:
 * <ul>
 * <li>date/time</li>
 * <li>severity (as string: INFO/WARNING/ERROR/FATAL)</li>
 * <li>text which describes the severity</li>
 * </ul>
 * the tab (\t) is used as field delimiter
 *
 * @author  Renato Steiner
 * @version $Id: SingleLineFormatter.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class SingleLineFormatter extends Formatter {

    protected static String LOG_FIELD_START = "[";
    protected static String LOG_FIELD_END = "]";

    /** Creates new SingleLineFormatter
     */
    public SingleLineFormatter() {
        setSeparator("\t");
        setDateFormat("dd.MM.yyyy");
        setTimeFormat("kk:mm:ss:SSS");
    }

    /** Overloaded method of Formatter, formats the log record
     * @return Formatted line
     * @param record the ILogRecord object containing the log informations
     */
    public java.lang.String format(ILogRecord record) {
        String SEP = getSeparator();

        long timeStamp = record.getTimeStamp();
        StringBuffer result = new StringBuffer();

        // Field 1: Date
        result.append(LOG_FIELD_START);
        result.append(getDate(timeStamp));
        result.append(" ");
        result.append(getTime(timeStamp));
        result.append(LOG_FIELD_END);
        result.append(SEP);

        // Field 2: Type
        result.append(getTypeText(record.getType()));
        result.append(SEP);

        String className = (String)record.getAttribute("loggingClass");
        if( className != null ) {
            className = extractClassName( className );
            result.append( "[" + className + "::" );
        }

        Object obj = record.getAttribute("loggingMethod");
        if( obj != null ) {
            result.append( obj + "]" + SEP );
        }

        //result.append( record.getText() + SEP );
        result.append( getFormattedText( record ) + SEP );

	return( result.toString() );
    }

    /**
     * This method returns the text of the logrecord. By default the
     * <code>com.ibm.logging.ILogRecord.getText</code> method is
     * invoked. Subclasses can provide an alternative implementation for example
     *
     * @param record the ILogRecord object containing the log information
     * @return the text string of the log record
     */
    protected String getFormattedText( ILogRecord record ) {
        return( record.getText() );
    }

    /**
     * This method returns a string represenatation of the appropriate message type
     *
     * @return Formatted line
     * @param record the IRecord Object containing the log-type as a long
     */
    protected static String getTypeText( long messType ) {

        if( messType == IRecordType.TYPE_ERR ) return "ERROR";
        if( messType == IRecordType.TYPE_FATAL ) return "SEVERE";
        if( messType == IRecordType.TYPE_INFO ) return "INFO";
        if( messType == IRecordType.TYPE_WARN ) return "WARNING";
        if( messType == IRecordType.TYPE_ENTRY ) return "ENTRY";   // method entry logging
        if( messType == IRecordType.TYPE_EXIT ) return "EXIT";     // method exit logging
        if( messType == IRecordType.TYPE_DEFAULT_TRACE ) return "TRACE";
        return("-");
    }

    /**
     * This method extracts the classname from the passed object. Only the classname is
     * returned, the leading package qualifier is omited.
     * E.g. com.acme.util.AClass is substituted to AClass
     *
     * @param aString a string from which the classname should be extracted
     * @return A string representing the classname without the leading package qualifier
     */
    protected String extractClassName(String className) {
        String name = className.toString();

        if(name.indexOf(".") != -1) {
            name = name.substring(name.lastIndexOf(".")+1, name.length());
        }
        return name;
    }
}

