package com.test;

/*
 * @(#)TestClass.java
 */

import java.text.SimpleDateFormat;
import java.util.*;
import com.other.*;

/**
 * This is a test class with an inner class.
 * Now let's see a simple bulleted list:
 * <ul>
 * <li>date/time
 * <li>severity (as string: <code>INFO/WARNING/ERROR/FATAL</code>)
 * <li>text which describes the severity
 * </ul>
 * and an image: <p>
 * <img src="doc-files/custom.jpg"/>
 * <p>And some preformatted text:
 * <pre>String permission = ...
 * ConfigurationProvider cp = &lt;ConfigurationProvider&gt;
 * Security security = SecurityFactory.getInstance(sc)
 * Ticket ticket = security.getTicket(&lt;Permission(permission)&gt;);</pre>
 *
 * @see com.other.OtherClass Andere Klasse
 * @see com.other.OtherClass OtherKlasse
 * @see com.other.OtherClass
 * @see com.other.OtherClass#someOtherMethod
 * @see com.other.OtherClass#someOtherMethod()
 * @see com.other.OtherClass#someOtherMethod(label)
 * @see com.other.OtherClass#someOtherMethod(String)
 * @see OtherClass
 * @see #sayHello(int)
 * @see #sayHello
 * @dummy Or is it not?
 * @author Frank Miller
 * @author Marcello Bello
 * @author Marie Curie
 * @since 1.0
 * @todo This is a custom tag.
 * @invalid This tag has no configured label, so it must NOT be printed,
 *          but a warn log message must be shown in the console.
 * @version $Id: TestClass.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class TestClass implements ITestInterface, ISecondInterface {

    /** This is a constant for something. */
    public static final int SOME_CONSTANT = 3;
    
    /** 
     * Creates new TestClass, 
     */
    public TestClass(){
        setSeparator("\t");
        setDateFormat("dd.MM.yyyy");
        setTimeFormat("kk:mm:ss:SSS");
    }

    /**
     * Another constructor.
     * @param value Some value.
     */
    public TestClass(String value) {
        
    }

    /**
     * Another constructor.
     * @param value Some value.
     */
    public TestClass(int value) {
        
    }

    /**
     * Another constructor.
     * @param value Some value.
     * @param otherValue some other value.
     */
    public TestClass(int value, long otherValue) {
        
    }

    
    /**
     * A method with a very short name. 
     */
    public void no() {
    }
    
    /**
     * Some test method. 
     */
    public void doit() {
    }

    /**
     * Another method with a very short name.
     * 
     * @param value
     * @param value
     */
    public void doit(int value, int value) {
    }

    /**
     * Another method with a very short name.
     * 
     * @param value
     * @param value
     * @param value
     */
    public void doit(int value, int value, String value) {
    }

    /**
     * Another method with a very short name.
     * 
     * @param value
     * @param value
     */
    public void doit(int value, String value) {
    }
    
    /*
     * Interface method
     */
	private void doNothing() {
	}
    
    /** 
     * Base-class method that will be overridden
     * in the subclass.
     * 
     * @throws IllegalArgumentException Blablabla
     * @exception RuntimeException This tests support for the 'exception' tag.
     * @exception ClassCastException If there was an invalid class.
     * @todo There is still stuff to do.
     */
    public void doAnyhing() throws RuntimeException, ClassCastException {
    }

	/**
	 * Lets see a simple bulleted list:
	 * <ul>
	 * <li>First <b>item</b> here</li>
	 * <li>Second item</li>
	 * </ul>
	 * And then some more text.
	 * 
	 * @return An array with all names or null.
	 * @throws RuntimeException Sometimes if something failed.
	 * @throws IllegalStateException If it feels like it.
	 */
	public String[] getNames() throws RuntimeException, IllegalStateException {
		return null;
	}

	/**
	 * Lets see a simple bulleted list:
	 * <ul>
	 * <li>First <b>item</b> here</li>
	 * <li>Second item</li>
	 * </ul>
	 * And then some more text.
	 * 
	 * @param arrayArgs This is an array of arguments, doh!
	 */
	public static void doNothing(String[] arrayArgs) {
	}

	/**
	 * Says hello to someone.
	 * 
	 * @param name The name
	 */
    public void sayHello(String name) {
	}
	
    /**
     * Another say hello method.
     * 
     * @param value Some value.
     */
    public void sayHello(int value) {
    }
    
    /**
     * Another say hello method.
     * 
     * @param value Some value.
     */
    public void sayHello(long value) {
    }
    
    /**
     * Another say hello method.
     * 
     * @param value Some value.
     * @param store A hashtable for something.
     */
    public void sayHello(Object value, Hashtable store) {
    }
    
    /**
     * This method returns the text of the logrecord.
     *
     * @param record the String containing the log information
     * @return the text string of the log record
     */
    protected String getFormattedText( String record ) {
        return null;
    }
    
    /**
     * This is a method to test lots of things (printing of tags,
     * linking of types etc.).
     * 
     * @param parm The first parameter.
     * @param parmTwo The second parameter.
     * @return Something of a certain type.
     * @throws CustomException The is our own exception.
     * @throws RuntimeException The is a very ugly exception.
     */
    public ITestInterface aTestMethod(ISecondInterface parm, IThirdInterface parmTwo)
        throws CustomException, RuntimeException {
        return null;
    }

    /**
     * Does something new.
     * 
     * @param text The text to say.
     * @filtered Tests the filter feature
     */
    public void sayNothing(String text) {
    }

    /**
     * This is the original text which will be inherited
     * by the subclasses.
     */
    public void inheritsDocMethod() {
    }
    
    /**
     * Some inner class for anything.
     * 
     * @author msc
     */
    public class MyInnerClass {
    	
    	/**
    	 * Method which returns some numeric value
    	 * based on some String key.
    	 * 
    	 * @param key The key string.
    	 * @return The numeric value.
    	 */
    	public int getValue(String key) {
    		return 0;
    	}

		/**
		 * Not that this is often used. But theoretically, inner
		 * classes can even be nested into other inner classes..
		 */    	
    	public class MyNestedInnerClass {
    		
    		/**
    		 * This method returns a text value.
    		 * 
    		 * @return The text value or null.
    		 */
    		public String getSomeValue() {
    			return null;
    		}
    	}
    }
    
    /**
     * Another inner class for testing how a 
     * second inner class is handled.
     * 
     * @author msc
     */
    public class AnotherInnerClass {
    	
    	/**
    	 * Method which returns some numeric value
    	 * based on some String key.
    	 * 
    	 * @param key The key string.
    	 * @return The numeric value.
    	 */
    	public int getAnotherValue(String key) {
    		return 0;
    	}
    }
}

