package com.other;

/*
 * @(#)OtherClass.java
 */
import com.test.ISecondInterface;

/**
 * This is a sub-interface of another interface.
 *
 * @author  Marcel Schoen
 * @version $Id: IThirdInterface.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 */
public interface IThirdInterface extends ISubInterface {

    /**
     * And a method again.
     *
     * @return true if it is annyoing
     */
    public boolean isAnnyoing();
}

