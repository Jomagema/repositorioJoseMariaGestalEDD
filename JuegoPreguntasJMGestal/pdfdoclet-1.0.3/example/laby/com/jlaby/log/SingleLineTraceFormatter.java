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
import java.text.MessageFormat;

/**
 * SingleLineTraceFormatter is used to output a single trace log-entry containing the following
 * format:
 * <ul>
 * <li>date/time</li>
 * <li>trace type (as string: enter,exit,trace)</li>
 * <li>text which describes the severity</li>
 * </ul>
 * the tab (\t) is used as field delimiter
 *
 * @author  Renato Steiner
 * @version $Id: SingleLineTraceFormatter.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class SingleLineTraceFormatter extends SingleLineFormatter {

    /** Creates new SingleLineTraceFormatter
     */
    public SingleLineTraceFormatter() {
        super();
    }

    /**
     * This method returns a formatted text, but it doesn't support logrecords which have more than
     * 10 arguments to print out.
     */
    public java.lang.String getFormattedText(ILogRecord record) {
        // $$$ do that more than 10 args are supported...
        // in the meantime just catch this problem in order to avoid a IllegalArgumentException
        if( record.getParameters().length > 10 ) {
            return( record.getText() );
        }
        else {
            return( MessageFormat.format( record.getText(), record.getParameters() ) );
        }
    }
}

