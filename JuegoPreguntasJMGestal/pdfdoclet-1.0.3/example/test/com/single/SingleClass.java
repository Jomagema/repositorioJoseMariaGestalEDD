package com.single;

/*
 * @(#)SingleClass.java
 */

/**
 * This is a poor, single class. It is used to
 * test the PDFDoclet's ability to create doc
 * for single classes, and not only for packages.
 * <ul>
 *   <li>Number one</li>
 *   <li>Number two</li>
 * </ul>
 * And then some text after the list. Now a nested list.
 * <ol>
 *   <li>List one
 *     <ul>
 *         <li>James</li>
 *         <li>Jones</li>
 *     </ul>
 *   </li>
 *   <li>Second</li>
 *   <li>List three
 *     <ul>
 *         <li>James</li>
 *         <li>Jones</li>
 *     </ul>
 *   </li>
 *   <li>Fourth</li>
 * </ol>
 *
 * @author  Marcel Schoen
 * @version $Id: SingleClass.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 */
public class SingleClass {

	/** 
	 * A public flag here.
	 * <ul>
	 *   <li>Number one</li>
	 *   <li>Number two</li>
	 * </ul>
	 * And some text.
	 */
	public final static int SOME_FLAG = 100;
    
    /** Yet another flag. */
    public final static int SECOND_FLAG = 200;
    
    /** The first flag. */
    public final static int FIRST_FLAG = 200;
	
    /** 
     * Returns NOT a <tt>java.util.List</tt> with anything.
     * Default constructor. List:
     * <ul>
     *   <li>Number one</li>
     *   <li>Number two</li>
     * </ul>
     * And some text.
     */
    public SingleClass(){
    }

    /**
     * Creates an element of the given qualified name and namespace URI.
     * @param namespaceURI The namespace URI of the element to create.
     * @param qualifiedName The qualified name of the element type to 
     *   instantiate.
     * @return A new table: <table border="1" bgcolor="#44AA44">
     *             <tr><td>Column one</td><td>Column two</td></tr>
     *             <tr><td>Column one</td><td>Column two</td></tr>
     *              </table>
     * @exception DOMException
     */
    public org.w3c.domElement createElementNS(String namespaceURI, 
                                   String qualifiedName)
                                   throws org.w3c.dom.DOMException {
        
    }
    
	/**
	 * This method does anything.
     * <ul>
     *   <li>Number one</li>
     *   <li>Number two</li>
     * </ul>
	 */
	private void doAnything(java.util.Properties props, String name, Object parm) throws java.io.IOException {
	}
	
	private class AnormalInnerClass {
		public void dummyMethod() {
		}
	}
}

