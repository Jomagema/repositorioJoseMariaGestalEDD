package com.test;

/*
 * @(#)SubClass.java
 */

/**
 * This is a subclass.
 *
 * The following "see" tag refers to a method of a super class. This
 * is quite a challenge. Here's an inline link first:
 * {@link com.test.TestClass Die Testklasse}
 * And then some more stuff.
 * <p>
 * Now we try a table with 3 rows and 3 columns...
 * <p>
 * <table align="left" width="90" border="1" cellpadding="2">
 *   <tr>
 *      <th>Column 1</th>
 *      <th>Column 2</th>
 *      <th>Column 3</th>
 *   </tr>
 *   <tr>
 *      <td>Row 1, Column 1</td>
 *      <td>Row 1, Column 2</td>
 *      <td>Row 1, Column 3</td>
 *   </tr>
 *   <tr>
 *      <td>Row 2, Column 1</td>
 *      <td colspan="2">A nested table:
 *                <table border="1" bgcolor="#EEEEEE"><tr><td>FIELD ONE</td><td>AND TWO</td>
 *                                 <td>AND THREE</td></tr></table>
 *       </td>
 *   </tr>
 * 
 *   <tr>
 *      <td>Row 3, some <b>bold</b> text</td>
 *      <td>Row 2, a list <ul><li>one</li><li>two</li></ul> as well</td>
 *      <td>Row 2, a {@link TestClass link} to some class</td>
 *   </tr>
 * 
 * </table>
 * 
 * @see #no
 * @see #doit
 * @see #doit(int,String)
 * 
 * @author  The one ({@inheritDoc})
 * @version $Id: SubClass.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 * @filtered Tests the filter feature
 * @see com.test.TestClass#no
 * @see com.test.TestClass#no()
 */
public class SubClass extends TestClass {

    /** 
     * Creates new TestClass
     */
    public SubClass(){
    }

	/**
	 * Does the same as the overriden method ({@inheritDoc}), but
	 * with a different prefix.
 * Now we try a table with 3 rows and 3 columns...
 * <table align="left" width="90" border="1" cellpadding="2">
 *   <tr>
 *      <th>Column 1</th>
 *      <th>Column 2</th>
 *      <th>Column 3</th>
 *   </tr>
 *   <tr>
 *      <td>Row 1, Column 1</td>
 *      <td>Row 1, Column 2</td>
 *      <td>Row 1, Column 3</td>
 *   </tr>
 *   <tr>
 *      <td>Row 2, Column 1</td>
 *      <td colspan="2">A nested table:
 *                <table border="1"><tr><td>FIELD ONE</td><td>AND TWO</td></tr></table>
 *       </td>
 *   </tr>
 * 
 *   <tr>
 *      <td>Row 3, some <b>bold</b> text</td>
 *      <td>Row 2, a list <ul><li>one</li><li>two</li></ul> as well</td>
 *      <td>Row 2, a {@link TestClass link} to some class</td>
 *   </tr>
 * 
 * </table>
	 * 
	 * @param name Same as before ({@inheritDoc}) but with 
	 *             a totally different meaning
	 */
    public ISecondInterface sayHello(String name) {
	}

    /**
     * This method uses four parameters.
     * 
     * @param one The first parameter
     * @param two The second parameter
     * @param three The third parameter
     * @param four The fourth parameter
     */
    public void useFourParms(String one, int two, Object three, char four) {
    }
    
    /**
     * This is the subclass method. The next paragraph shows
     * the doc inherited from the TestClass.
     * <p>
     * {@inheritDoc}
     * @filtered Tests the filter feature
     */
    public void inheritsDocMethod() {
    }
}

