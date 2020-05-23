package com.jlaby.util;

/*
 * @(#)StringUtil.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */


/**
 * Generic String handling toolbox.
 *
 * @author  Marcel Schoen
 * @version $Id: StringUtil.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 */
public class StringUtil {

    /**
     * Sort a String array ascending.
     *
     * @param source The String array which will be sorted.
     * @return The new array, sorted in ascending order.
     */
    public static String[] sortAscending(String[] source) {
        String[] upperArray = new String[source.length];
        byte[][] entries = new byte[source.length][0];
        byte[] entry1 = null, entry2 = null, swapentry = null;
        byte value1 = 0, value2 = 0;
        int i = 0, u = 0, z = 0, len = 0, bigger = 0;
        String swap = null;

        for(i=0; i<source.length; i++)
            entries[i] = source[i].toUpperCase().getBytes();

        for(i=0; i<source.length-1; i++) {
            entry1 = entries[i];
            for(u=i+1; u<source.length; u++) {
                entry2 = entries[u];
                if(entry1.length < entry2.length)
                    len = entry1.length;
                else
                    len = entry2.length;
                bigger = -1;
                for(z=0; z<len && bigger == -1; z++) {
                    value1 = entry1[z];
                    value2 = entry2[z];
                    if(value2 < value1)
                        bigger = 1;
                    if(value2 > value1)
                        bigger = 0;
                }
                if(bigger == -1 && entry2.length < entry1.length)
                    bigger = 1;
                //--- first entry higher? ----
                if(bigger == 1) {
                    swapentry = entries[i];
                    entries[i] = entries[u];
                    entries[u] = swapentry;
                    swap = source[i];
                    source[i] = source[u];
                    source[u] = swap;
                    entry1 = entries[i];
                }
            }
        }
        return source;
    }

    /**
     * Run through all entries of a string array and patch every
     * textstring by replacing one or several occurrences of
     * a text in it with another text.
     *
     * @param text The text array that should be patched.
     * @param old The text that should be replaced.
     * @param patch The replacement text.
     * @return The patched text array.
     */
    public static String[] patch(String[] text, String old, String patch) {
        String[] result = new String[text.length];
        for(int i=0; i<result.length; i++) {
            if(text[i].indexOf(old) == -1)
                result[i] = new String(text[i]);
            else
                result[i] = patch(text[i], old, patch);
        }
        return result;
    }

    /**
     * Patch a textstring by replacing one or several occurrences of
     * a text in it with another text.
     *
     * @param text The text that should be patched.
     * @param old The text that should be replaced.
     * @param patch The replacement text.
     * @return The patched text.
     */
    public static String patch(String text, String old, String patch) {
        String result = new String(text);
        int pos = 0, len = old.length();
        while((pos = result.indexOf(old)) != -1) {
            result = result.substring(0,pos) + patch + result.substring(pos+len,result.length());
        }
        return result;
    }

    /**
     * Get the ASCII-code of a single character.
     *
     * @param txt A string containing a single character.
     * @return The ASCII-value of the character.
     */
    public static int getASCII(String txt) {
        char[]  zeichen = new char[1];
        txt.getChars(0,1,zeichen,0);
        return (int)zeichen[0];
    }

    /**
     * Returns the character for a certain ASCII-code.
     *
     * @param ascii The ascii code (value from 0 - 255).
     * @return A String containing a single character.
     */
    public static String getCharacter(int ascii) {
        Character zeichen = new Character((char)ascii);
        return zeichen.toString();
    }
}
