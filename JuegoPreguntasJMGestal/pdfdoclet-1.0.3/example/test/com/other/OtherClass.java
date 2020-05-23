package com.other;

/*
 * @(#)OtherClass.java
 */

import java.text.SimpleDateFormat;
import java.util.Date;

import com.test.SubClass;

/**
 * Blah blah blah blah...
 *
 * @author  Marcel Schoen
 * @version $Id: OtherClass.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 * @filtered Tests the filter feature
 */
public class OtherClass extends SubClass implements IThirdInterface {

    /** 
     * Creates new other class,
     * {@link com.test.SubClass SubClass} also helps.
     * 
     * @see com.test.TestClass#sayHello(long)
     * @see com.test.ITestInterface#initialize
     * @see com.test.ITestInterface
     * @see ITestInterface
     * @filtered Tests the filter feature
     */
    public OtherClass(){
        setSeparator("\t");
        setDateFormat("dd.MM.yyyy");
        setTimeFormat("kk:mm:ss:SSS");
    }
    
    public boolean isAnnyoing() {
        
    }
    
    /**
     * This is another method for something.
     * 
     * @param label Says everything, doesn't it?
     */
    public void someOtherMethod(String label) {
    }
    
    /**
     * This method takes a parameter of a type which 
     * is part of this API. That should create an
     * internal link.
     * 
     * @param someValue The parameter value.
     * @return The return value.
     * @filtered Tests the filter feature
     */
    public ISubInterface doAction(IThirdInterface someValue) {
    }
    
    public void useFourParms(String one, int two, Object three, char four) {
    }

    /**
     * This is the otherclass method. The following paragraph shows
     * the doc inherited from the SubClass.
     * <p>
     * {@inheritDoc}
     */
    public void inheritsDocMethod() {
    }
}

