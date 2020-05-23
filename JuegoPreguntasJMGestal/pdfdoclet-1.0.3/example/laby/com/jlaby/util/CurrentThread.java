/*
 * @(#)CurrentThread.java
 *
 * @Copyright: TARSEC AG, Switzerland, 2002, All Rights Reserved.
 */

package com.jlaby.util;

import java.util.Hashtable;

/**
 * Maps a name (for log entries) to the reference of 
 * threads. This is necessary in order to be able to
 * find out which log entries in a log file belong
 * to the same request thread (if several requests
 * were handled at the same time).
 *
 * @author  Marcel Schoen
 * @version $Id: CurrentThread.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 */
public class CurrentThread {

    private static Hashtable threadNames = new Hashtable();
    private static Hashtable bareThreadNames = new Hashtable();

    /**
     * Removes the current thread from the internal list.
     */
    public static void remove() {
        threadNames.remove(Thread.currentThread());
    }

    /**
     * Adds the current thread to the internal list
     * with its current name.
     */
    public static void add() {
        if(threadNames.get(Thread.currentThread()) == null) {
            String threadName = Thread.currentThread().toString();
            if(threadName.startsWith("Thread[Acceptor")) {
                threadName = "Acceptor";
                bareThreadNames.put(Thread.currentThread(), threadName);
                threadNames.put(Thread.currentThread(), "{" + threadName + "} ");
            } else if(threadName.indexOf("[SSL") != -1) {
                threadName = threadName.substring(threadName.indexOf("-")+1, threadName.indexOf("[SSL"));
                threadName = "remote|" + threadName.substring(0, threadName.lastIndexOf("|"));
                bareThreadNames.put(Thread.currentThread(), threadName);
                threadNames.put(Thread.currentThread(), "{" + threadName + "} ");
            } else if(threadName.indexOf("Socket[") != -1) {
                threadName = threadName.substring(threadName.indexOf("-")+1, threadName.length());
                threadName = "local|" + threadName.substring(0, threadName.indexOf("Socket[")-1);
                bareThreadNames.put(Thread.currentThread(), threadName);
                threadNames.put(Thread.currentThread(), "{" + threadName + "} ");
            } else {
                threadName = Thread.currentThread().getName();
                bareThreadNames.put(Thread.currentThread(), threadName);
                threadNames.put(Thread.currentThread(), "{" + threadName + "} ");
            }
        }
    }

    /**
     * Adds the current thread to the internal list
     * with a given name.
     *
     * @param threadName The name for the thread.
     */
    public static void add(String threadName) {
        if(threadNames.get(Thread.currentThread()) == null) {
            bareThreadNames.put(Thread.currentThread(), threadName);
            threadNames.put(Thread.currentThread(), "{" + threadName + "} ");
        }
    }

    /**
     * Returns the name of the current Thread in the
     * internal list.
     *
     * @return The name of the current thread with surrounding
     *         brackets and blanks (ready to be used in other Strings).
     */
    public static String getName() {
        String name = (String)threadNames.get(Thread.currentThread());
        if(name == null) {
            name = "?" + Thread.currentThread().toString() + "? ";
        }
        return name;
    }

    /**
     * Returns the name of the current Thread in the
     * internal list.
     *
     * @return The bare name of the current thread.
     */
    public static String getBareName() {
        String name = (String)bareThreadNames.get(Thread.currentThread());
        if(name == null) {
            name = "?" + Thread.currentThread().toString() + "? ";
        }
        return name;
    }
}
