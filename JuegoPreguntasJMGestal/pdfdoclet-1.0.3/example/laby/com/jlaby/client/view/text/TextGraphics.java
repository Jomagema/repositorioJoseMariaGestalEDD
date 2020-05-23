package com.jlaby.client.view.text;

/*
 * @(#)TextGraphics.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import java.util.*;
import com.jlaby.util.*;
import com.jlaby.config.*;

/**
 * Helper class which makes it easier to implement
 * objects for the laby. It provides some methods
 * to load graphics from resource files in the classpath etc.
 *
 * @author  Marcel Schoen
 * @version $Id: TextGraphics.java,v 1.1 2013/10/27 23:51:26 marcelschoen Exp $
 */
public abstract class TextGraphics {

    // **************************************************
    // Internal static variables
    // **************************************************
    private static Hashtable ms_loadedGraphics = new Hashtable();
    private static Properties ms_colorTable = new Properties();
    private static Hashtable ms_colorChars = new Hashtable();
    private static boolean ms_initialized = false;

    // **************************************************
    // CONSTANTS
    // **************************************************

    /**
     * Flags for the color settings
     */
    public final static int SINGLE_COLOR = 1;
    public final static int MULTI_COLOR = 2;

    /**
     * Flags for the graphics type
     */
    public final static int SINGLE_SIDED = 1;
    public final static int MULTI_SIDED = 2;

    /**
     * Constants for the direction flag of an object
     */
    public final static int UNDEFINED = -1;  // for single-sided objects
    public final static int LOOKING_AWAY = 1;
    public final static int FACING_PLAYER = 2;
    public final static int LOOKING_RIGHT = 3;
    public final static int LOOKING_LEFT = 4;

    // **************************************************
    // VARIABLES
    // **************************************************
    protected int m_colorType = TextGraphics.SINGLE_COLOR;
    protected String m_color = "#FFFFFF";
    protected int m_type = TextGraphics.SINGLE_SIDED;
    protected String m_name = "<UNKNOWN>";
    protected Properties m_data = new Properties();

    public TextGraphics() {
        if(!ms_initialized) {
            initialize();
        }
    }

    public static void initialize() {
        // prepare to read the HTML file which contains the color code map
        String colorfile = Configuration.getProperty("graphics.text.factory.colortable");
        try {
            // 1st try: Read it from file in filesystem
            String[] txt = null;
            File test = new File(colorfile);
            if(test.exists() && test.isFile()) {
                txt = FileUtil.readTextFile(test.getAbsolutePath());
                ms_initialized = true;
            } else {
                // 2nd try: Read it from file in classpath
                InputStream in = colorfile.getClass().getResourceAsStream(colorfile);
                if(in != null) {
                    txt = FileUtil.readTextFileStream(new InputStreamReader(in));
                    ms_initialized = true;
                }
            }
            String character = "A";
            for(int i=0; i<txt.length; i++) {
                character = parseHTML(txt[i], character);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static String parseHTML(String txt, String oldCharacter) {
        boolean insideCell = false;
        String newCharacter = oldCharacter;
        int x = 0;
        String stuff = new String(txt);
        while((x = stuff.indexOf("<td")) != -1) {
            stuff = stuff.substring(x+1, stuff.length());
            String cellText = stuff.substring(0, stuff.indexOf("</td>"));
            if(cellText.indexOf("#") != -1) {
                String color = cellText.substring(cellText.indexOf("#"), cellText.length()).toUpperCase();
                color = color.substring(0, color.indexOf("\""));
                ms_colorTable.put(newCharacter.trim(), color);
                ms_colorChars.put(color, newCharacter);
            } else {
                newCharacter = cellText.substring(cellText.indexOf(">")+1, cellText.length());
            }
        }
        return newCharacter;
    }

    /**
     * Constructs the
     */
    public void init(String name, int type,
                     int colorType, String color,
                     Properties data) {
        m_data = data;
        m_colorType = colorType;
        m_color = color;
        m_type = type;
        m_name = name;
    }

    public static String getColorCharacter(String color) {
        String character = " ";
        if(color == null) {
            return " ";
        }
        if(ms_colorChars.get(color) != null) {
            character = (String)ms_colorChars.get(color);
        }
        return character;
    }

    public static String getColorValue(String character) {
        return (String)ms_colorTable.get(character);
    }

    public static String getColorValue(char character) {
        char[] chars = new char[1];
        chars[0] = character;
        return getColorValue(new String(chars));
    }

    public String getName() {
        return m_name;
    }

    public String getColor() {
        return m_color;
    }

    public int getColorType() {
        return m_colorType;
    }

    public int getType() {
        return m_type;
    }

    /**
     * This method is called by the view renderer class.
     */
    public String[] getGraphics(int position, int direction, int distance) {
        String[] result = null;
        try {
            result = getGraphicsData(position, direction, distance);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * This method is called by the view renderer class.
     */
    public String[] getMask(int position, int direction, int distance) {
        String[] result = null;
        try {
            result = getMaskData(position, direction, distance);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * This method must return an array of Strings of same length containing
     * the graphics of the object specified by position, direction and distance.
     * NOTE: If the object is invisible from the current point of view (a writing on
     * a wall, for example, is not visible from the other side of the wall) the
     * method should return an array with 0 entries (String[0]).
     *
     * @param position The number of the field in the view area (see above).
     * @param direction The direction which the object is looking into (see constants)
     * @param distance The distance from the viewer to the object (0-5)
     * @return An array of Strings of same length, containing the graphics of the object,
     *         or a String-array with length 0 if the object is invisible.
     */
    public abstract String[] getGraphicsData(int position, int direction, int distance);

    /**
     * This method must return an array of Strings of same length containing
     * the graphics of the object specified by position, direction and distance.
     * NOTE: If the object is invisible from the current point of view (a writing on
     * a wall, for example, is not visible from the other side of the wall) the
     * method should return an array with 0 entries (String[0]).
     *
     * @param position The number of the field in the view area (see above).
     * @param direction The direction which the object is looking into (see constants)
     * @param distance The distance from the viewer to the object (0-5)
     * @return An array of Strings of same length, containing the graphics of the object,
     *         or a String-array with length 0 if the object is invisible.
     */
    public abstract String[] getMaskData(int position, int direction, int distance);
}
