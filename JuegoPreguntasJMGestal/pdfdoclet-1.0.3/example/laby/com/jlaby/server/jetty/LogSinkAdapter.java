/*
 * @(#)LogSinkAdapter.java
 *
 * @Copyright: Marcel Schoen, Switzerland, 2002, All Rights Reserved.
 */
package com.jlaby.server.jetty;

import org.mortbay.util.LogSink;
import org.mortbay.util.Frame;

import com.jlaby.log.Log;

/**
 * This facade class catches log calls from the Jetty
 * server in order to log them through TARSECs'
 * standard logger (like log4j, for instance).
 * This ensures that the log messages will all have
 * the same format.
 *
 * @author Marcel Schoen
 * @version $Id: LogSinkAdapter.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class LogSinkAdapter implements LogSink {

    private String options = "";
    private boolean isStarted = false;
    private boolean isDestroyed = false;

    // ****************************************
    // LogSink interface implementation
    // ****************************************

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void log(String tag, Object msg, Frame frame, long time) {
        Log.trace(this, "log", "Tag: "+tag+", msg: "+msg.toString());
    }

    public void log(String formattedLog) {
        int endBracket = formattedLog.indexOf("]");
        if(endBracket != -1) {
            String msg = formattedLog.substring(endBracket+2, formattedLog.length());
            Log.trace(this, "log", msg);
        } else {
            Log.trace(this, "log", formattedLog);
        }
    }

    //****************************************
    // LifeCycle interface
    //****************************************
    
    public void start() {
        isStarted = true;
    }

    public void stop() {
        isStarted = false;
    }

    public void destroy() {
        isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public boolean isStarted() {
        return isStarted;
    }
}
