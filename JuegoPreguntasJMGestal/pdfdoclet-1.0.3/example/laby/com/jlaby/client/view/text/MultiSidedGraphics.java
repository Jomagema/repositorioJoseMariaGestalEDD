package com.jlaby.client.view.text;

/*
 * @(#)MultiSidedGraphics.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import java.util.Properties;

/**
 * Helper class which makes it easier to implement
 * objects for the laby. It provides some methods
 * to load graphics from resource files in the classpath etc.
 *
 * @author  Marcel Schoen
 * @version $Id: MultiSidedGraphics.java,v 1.1 2013/10/27 23:51:26 marcelschoen Exp $
 */
public class MultiSidedGraphics extends TextGraphics {

    /**
     * Default implementation for multi-sided objects.
     */
    public String[] getGraphicsData(int position, int direction, int distance) {

        String[] textLines = null;
        String directionText = "";
        if(direction == TextGraphics.LOOKING_AWAY) {
            directionText = ".away.";
        }
        if(direction == TextGraphics.FACING_PLAYER) {
            directionText = ".facing.";
        }
        if(direction == TextGraphics.LOOKING_LEFT) {
            directionText = ".left.";
        }
        if(direction == TextGraphics.LOOKING_RIGHT) {
            directionText = ".right.";
        }

        String distanceText = String.valueOf(distance);
        if(m_data.getProperty("width" + directionText + distanceText) == null) {
            // If no graphics can be found for this direction,
            // the object is invisible from this viewpoint.
            // An array with 0 lines will be returned.
            textLines = new String[0];
        } else {

            String widthText = m_data.getProperty("width" + directionText + distanceText);
            String heightText = m_data.getProperty("height" + directionText + distanceText);

            int width = Integer.parseInt(widthText.trim());  // width info not necessary at all?
            int height = Integer.parseInt(heightText.trim());

            textLines = new String[height];
            for(int i=0; i<height; i++) {
                textLines[i] = m_data.getProperty("line" + directionText + distanceText + "." + String.valueOf(i+1));
                textLines[i] = textLines[i].substring(1, width+1);
            }
        }
        return textLines;
    }

    /**
     * Default implementation for multi-sided objects.
     */
    public String[] getMaskData(int position, int direction, int distance) {

        String[] textLines = null;
        String directionText = "";
        if(direction == TextGraphics.LOOKING_AWAY) {
            directionText = ".away.";
        }
        if(direction == TextGraphics.FACING_PLAYER) {
            directionText = ".facing.";
        }
        if(direction == TextGraphics.LOOKING_LEFT) {
            directionText = ".left.";
        }
        if(direction == TextGraphics.LOOKING_RIGHT) {
            directionText = ".right.";
        }

        String distanceText = String.valueOf(distance);
        if(m_data.getProperty("width" + directionText + distanceText) == null) {
            // If no graphics can be found for this direction,
            // the object is invisible from this viewpoint.
            // An array with 0 lines will be returned.
            textLines = new String[0];
        } else {

            String widthText = m_data.getProperty("width" + directionText + distanceText);
            String heightText = m_data.getProperty("height" + directionText + distanceText);

            int width = Integer.parseInt(widthText.trim());  // width info not necessary at all?
            int height = Integer.parseInt(heightText.trim());

            textLines = new String[height];
            for(int i=0; i<height; i++) {
                textLines[i] = m_data.getProperty("mask" + directionText + distanceText + "." + String.valueOf(i+1));
                textLines[i] = textLines[i].substring(1, width+1);
            }
        }
        return textLines;
    }
}

