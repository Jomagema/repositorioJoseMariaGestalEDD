package com.other;

/*
 * @(#)OtherClass.java
 */

import com.test.ITestInterface;

/**
 * This is a sub-interface of another interface.
 *
 * @author  Marcel Schoen
 * @author Frank Miller
 * @version $Id: ISubInterface.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 * @filtered Tests the filter feature
 */
public interface ISubInterface extends ITestInterface {

    /**
     * Some method for something.
     *
     * @return true if there is anything.
     * @filtered Tests the filter feature
     */
    public boolean isAnything();
}

