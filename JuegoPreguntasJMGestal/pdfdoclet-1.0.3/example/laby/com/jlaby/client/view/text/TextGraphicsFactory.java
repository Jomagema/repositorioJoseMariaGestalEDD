package com.jlaby.client.view.text;

/*
 * @(#)TextGraphicsFactory.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import java.util.*;

import com.jlaby.exception.*;
import com.jlaby.log.Log;
import com.jlaby.util.*;

/**
 * Helper class which makes it easier to implement
 * objects for the laby. It provides some methods
 * to load graphics from resource files in the classpath etc.
 *
 * @author  Marcel Schoen
 * @version $Id: TextGraphicsFactory.java,v 1.1 2013/10/27 23:51:26 marcelschoen Exp $
 */
public class TextGraphicsFactory {

    // **************************************************
    // CONSTANTS
    // **************************************************
    private static Hashtable ms_loadedGraphics = new Hashtable();
    private static Properties ms_index = new Properties();

    public static void initialize(String indexProperties) {
        // read the properties for the mapping of text graphics and object types
        Log.info("TextGraphicsFactory", "initialize", "Load properties from "+indexProperties);
        ms_index = PropertyUtil.load(indexProperties);
    }

    /**
     * Factory method which caches all graphics in a static
     * Hashtable to ensure that they are loaded only once
     * during the Servlets lifespan.
     */
    public static TextGraphics load(int objectType) {
        String resourceName = ms_index.getProperty("no."+objectType);
        TextGraphics obj = null;
        if(ms_loadedGraphics == null) {
            Util.println("<P>Hashtable reference null.<P>");
        }
        if(resourceName == null) {
            Util.println("<P>Resourcename is null. Property name: no."+objectType+"<P>");
        }
        if(ms_loadedGraphics.get(resourceName) == null) {
            try {
                Log.info("TextGraphics", "load", "Load graphics data from: "+resourceName);
                Properties data = PropertyUtil.load(resourceName);

                String name = data.getProperty("graphics.name");
                String color = "#FFFFFF";
                Hashtable colorMap = new Hashtable();
                String graphicsTypeText = data.getProperty("graphics.type");
                String colorTypeText = data.getProperty("graphics.color");

                int colorType = TextGraphics.SINGLE_COLOR;
                if(colorTypeText != null && colorTypeText.toUpperCase().trim().equals("MULTI")) {
                    colorType = TextGraphics.MULTI_COLOR;
                } else {
                    color = TextGraphics.getColorValue(colorTypeText);
                }

                int graphicsType = TextGraphics.SINGLE_SIDED;
                if(graphicsTypeText != null && graphicsTypeText.toUpperCase().trim().equals("MULTI")) {
                    obj = new MultiSidedGraphics();
                    graphicsType = TextGraphics.MULTI_SIDED;
                }
                if(graphicsTypeText != null && graphicsTypeText.toUpperCase().trim().equals("SINGLE")) {
                    obj = new SingleSidedGraphics();
                    graphicsType = TextGraphics.SINGLE_SIDED;
                }
                if(graphicsTypeText != null && graphicsTypeText.toUpperCase().trim().equals("WALL")) {
                    obj = new WallGraphics();
                    graphicsType = TextGraphics.MULTI_SIDED;
                }

                obj.init(name, graphicsType, colorType, color, data);

                ms_loadedGraphics.put(resourceName, obj);
            } catch(Exception e) {
                ExceptionHandler.handleException(e);
            }
        } else {
            obj = (TextGraphics)ms_loadedGraphics.get(resourceName);
        }
        return obj;
    }
}
