package com.dummy;

import com.test.ITestInterface;

/*
 * @(#)Dummy.java
 */

/**
 * This class exists only to fill the "stuff" package with something.
 *
 * @myTag   Mixed case tag
 * @MYTAG   Upper case tag
 * @author  Marcel Schoen
 * @version $Id: Dummy.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class Dummy {
	
    /** 
     * Creates an instance of this dummy class.
     */
    public Dummy(){
    }

    /**
     * Gets some dummy text.
     * 
     * @param receiver This is an absolutely useless parameter.
     * @param args And these are the even more useless arguments.
     * @return The most useless text strings.
     */
    public static String[] getSomething(ITestInterface receiver, Object[] args) {
        
    }
}

