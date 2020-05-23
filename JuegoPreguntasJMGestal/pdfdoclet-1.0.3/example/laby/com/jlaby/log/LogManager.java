/*
 * @(#)LogManager.java
 *
 * @Copyright: Marcel Schoen, Switzerland, 2002, All Rights Reserved.
 */

package com.jlaby.log;

import java.io.File;
import java.util.Properties;

import com.ibm.logging.ILogger;
import com.ibm.logging.MessageLogger;
import com.ibm.logging.TraceLogger; 
import com.ibm.logging.FileHandler;
import com.ibm.logging.ConsoleHandler;
import com.ibm.logging.AnyMaskFilter;
import com.ibm.logging.IRecordType;

import com.ibm.logging.Formatter;

import com.jlaby.config.Configuration;
import com.jlaby.util.ILabyConstants;

/**
 * The LogManager ... 
 *
 * log.dir = directory for log files
 * log.name = logical name of log (internal)
 * log.desc = description of log (internal)
 * log.file = name of log file
 * log.trace = name of trace file
 * 
 * @author  Marcel Schoen
 * @version $Id: LogManager.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class LogManager implements ILabyConstants {

    private static final String DEFAULT_FILE = "messages.log";
    private static final String DEFAULT_DIR = "logs";

    // Message Logger
    private static MessageLogger msgLogger = null;

    // Error- and Warning Logger
    private static MessageLogger errLogger = null;
    
    // Trace Logger 
    private static TraceLogger traceLogger = null; 

    /** 
     * This variable holds a reference to the one and only LogManager instance 
     */ 
    private static LogManager logManagerRef = null; 
    
    /** 
     * Default private constructor which is invoked by the getInstance() method 
     */ 
    private LogManager() {
        this.initMessageLogger(); 
        this.initErrorLogger(); 
        this.initTraceLogger(); 
    }
    
    /** 
     * Initializes a message logger instance 
     */   
    private void initMessageLogger() {
        int msgMaxFileSize = -1;
        int msgMaxFiles = -1;

        String logDir = Configuration.getProperty(PROP_LOG_DIR, DEFAULT_DIR);
        String msgLogFilename = endPathWithSeparator(logDir) + 
            Configuration.getProperty(PROP_LOG_FILE, DEFAULT_FILE);
        String msgName = Configuration.getProperty(PROP_LOG_NAME);
        String msgDesc = Configuration.getProperty(PROP_LOG_DESC);

        msgLogger = createLogger( msgName,
                                  msgDesc, 
                                  msgLogFilename, 
                                  msgMaxFileSize, 
                                  msgMaxFiles,
                                  true ); // logging on 
    }
    
    /** 
     * Initializes an error- and warning logger instance 
     */   
    private void initErrorLogger() {
        int errMaxFileSize = -1;
        int errMaxFiles = -1;

        String logDir = Configuration.getProperty(PROP_LOG_DIR, DEFAULT_DIR);
        String errLogFilename = endPathWithSeparator(logDir) + 
            Configuration.getProperty(PROP_LOG_FILE, DEFAULT_FILE);
        String errName = Configuration.getProperty(PROP_LOG_NAME);
        String errDesc = Configuration.getProperty(PROP_LOG_DESC);

        errLogger = createLogger( errName,
                                  errDesc, 
                                  errLogFilename, 
                                  errMaxFileSize, 
                                  errMaxFiles,
                                  true ); // logging on 
    }

    /** 
     * Initializes a trace logger instance 
     */   
    private void initTraceLogger() {
        int traceMaxFileSize = -1;
        int traceMaxFiles = -1;

        String logDir = Configuration.getProperty(PROP_LOG_DIR, DEFAULT_DIR);
        String traceLogFilename = endPathWithSeparator(logDir) +
            Configuration.getProperty(PROP_LOG_FILE, DEFAULT_FILE);
        String traceName = Configuration.getProperty(PROP_LOG_NAME);
        String traceDesc = Configuration.getProperty(PROP_LOG_DESC);
        
        traceLogger = createTraceLogger( traceName,
                                         traceDesc, 
                                         traceLogFilename, 
                                         traceMaxFileSize, 
                                         traceMaxFiles,
                                         true ); // logging on 
    }

    /**
     * Checks if a given directory path ends with a
     * path separator character and, if not, 
     * concatenates such a character. The purpose
     * of this method is to make some String-concatenation
     * operations in the code more robust.
     *
     * @param The path of the directory.
     * @return The path of the same directory, ending with
     *         a file separator character.
     */
    private static String endPathWithSeparator(String path) {
        String newPath = path;
        if(!newPath.endsWith(File.separator)) {
            newPath = newPath.concat(File.separator);
        }
        return newPath;
    }

    /**
     * Returns a reference to an existing LogManager object. If no LogManager instance
     * was created to this point, a new one is created with the default
     * constructor and its reference is returned. If any other class
     * created an instance of the LogManager class before, the first created
     * instance will be returned.
     *
     * @return LogManager Reference to a LogManager object 
     */
    public static LogManager instance() {
        if( logManagerRef == null ) {
            logManagerRef = new LogManager(); 
            return( logManagerRef ); 
        }
        else {
            return( logManagerRef ); 
        }
    } 
    
    /**
     * Returns the message logger
     *
     * @return the message logger
     */
    public MessageLogger getMessageLogger() {
        return( msgLogger );
    }
    
    /**
     * Returns the error logger
     *
     * @return the error logger
     */
    public MessageLogger getErrorLogger() {
        return( errLogger );
    }
   
    /**
     * Returns the trace logger
     *
     * @return the trace logger
     */
    public TraceLogger getTraceLogger() {
        return( traceLogger ); 
    }
    
    /**
     * Creates the message logger
     * @return the message logger
     */
    private synchronized MessageLogger createLogger(String name, String desc,
                                                    String filename, int maxFileSize, int maxFiles,
                                                    boolean stateOn) {
       
        FileHandler log = new FileHandler("log", "", filename);
        
        SingleLineFormatter slFormatter = new SingleLineFormatter();
        log.addFormatter(slFormatter); 
        
        MessageLogger logger = new MessageLogger(name, desc);
        
        // Enable logger's synchronous mode to ensure immediate
        // buffer flush
        logger.setSynchronous(true);
        
        // add the file handler to the logger
        logger.addHandler(log);
        logger.setLogging(stateOn);
        return logger;
    }
    
    /**
     * Creates the message logger
     * @return the message logger
     */
    private synchronized TraceLogger createTraceLogger(String name, String desc,
                                                       String filename, int maxFileSize, int maxFiles,
                                                       boolean stateOn) {
       
        FileHandler log = new FileHandler("log", "", filename);

        SingleLineFormatter slFormatter = new SingleLineFormatter();
        log.addFormatter(slFormatter); 
        
        TraceLogger logger = new TraceLogger(name, desc);
        
        // Enable logger's synchronous mode to ensure immediate
        // buffer flush
        logger.setSynchronous(true);
        
        // add the file handler to the logger
        logger.addHandler(log);
        logger.setLogging(false);
        return logger;
    }
}
