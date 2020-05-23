package com.jlaby.exception;

/*
 * @(#)StackTraceCollector.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class allows to collect a stack trace output
 * into a string array which can then be used, for
 * example, to log the stack trace in a file.
 *
 * @author  Marcel Schoen
 * @version $Id: StackTraceCollector.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class StackTraceCollector extends OutputStream {

    private String[] stackTraceText = { "---stuff---" };
    private Vector textLines = new Vector();

    /**
     * Constructs a collector with a given exception.
     *
     * @param exception the exception whose stack trace
     *                  should be collected.
     */
    public StackTraceCollector(Throwable exception) {
	exception.printStackTrace(new PrintStream(this));
    }

    /**
     * Returns the stack trace of the exception given
     * in the constructor as an array of String objects.
     *
     * @return the String array with the stack trace.
     */
    public String[] getStackTrace() {
	stackTraceText = new String[textLines.size()];
	for(int i=0; i<stackTraceText.length; i++) {
	    stackTraceText[i] = (String)textLines.elementAt(i);
	}
	return stackTraceText;
    }


    /**
     * Method not implemented, but necessary because it is abstract
     * in the superclass.
     */
    public void write(int val) {
	//**** DO NOT REMOVE THIS METHOD *****
    }

    /**
     * Implementation of the abstract method from the superclass.
     */
    public void write(byte[] data) {
	textLines.addElement(new String(data));
	//	    addLine("1:"+(new String(data)));
    }

    /**
     * Implementation of the abstract method from the superclass.
     */
    public void write(byte[] data, int off, int len) {
	char[] output = new char[len];
	int pos = 0, i=0;
	for(i=off; i<off+len; i++) {
	    output[pos++] = (char)data[i];
	}
	String[] res = convert(output);
	if(res == null) {
	    return;
	}
	for(i=0; i<res.length; i++) {
	    textLines.addElement(res[i]);
	}
    }


    /**
     * Converts an array of characters to a String.
     */
    private final String[] convert(char[] data) {
	String[] lines = null;
	int newlines = 0, start = 0, len = 0;

	if(data.length == 1 && (data[0] == '\r' || data[0] == '\n'))
	    return null;
	if(data.length == 2 && data[0] == '\r' && data[1] == '\n')
	    return null;

	for(int i=0; i<data.length; i++)
	    if(data[i] == '\r')
		newlines++;
	if(newlines == 0)
	    lines = new String[1];
	else
	    lines = new String[newlines];
	int ct = 0;
	for(int line=0; line<lines.length; line++) {
	    lines[line] = "";
	    while(ct<data.length && data[ct] != '\r') {
		if(data[ct] == '\t') {
		    lines[line] = lines[line].concat("   ");
		    data[ct] = ' ';
		}
		ct++;
		len++;
	    }

	    lines[line] = lines[line] + (new String(data,start,len));
	    len = 0;
	    if(ct < data.length && data[ct] == '\n')
		ct++;
	    start = ct;
	}
	return lines;
    }
}
