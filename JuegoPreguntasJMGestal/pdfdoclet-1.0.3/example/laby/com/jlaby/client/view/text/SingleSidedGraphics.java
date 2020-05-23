package com.jlaby.client.view.text;

/*
 * @(#)SingleSidedGraphics.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import java.util.*;

import com.jlaby.exception.*;
import com.jlaby.log.*;
import com.jlaby.util.*;

/**
 * Helper class which makes it easier to implement
 * objects for the laby. It provides some methods
 * to load graphics from resource files in the classpath etc.
 *
 * @author  Marcel Schoen
 * @version $Id: SingleSidedGraphics.java,v 1.1 2013/10/27 23:51:26 marcelschoen Exp $
 */
public class SingleSidedGraphics extends TextGraphics {

    /**
     * Default implementation for single-sided objects.
     */
    public String[] getGraphicsData(int position, int direction, int distance) {
        String distanceText = String.valueOf(distance);
        String widthText = m_data.getProperty("width." + distanceText);
        String heightText = m_data.getProperty("height." + distanceText);

        String[] textLines = null;
        if(widthText != null && heightText != null) {
            try {
                int width = Integer.parseInt(widthText.trim());  // width info not necessary at all?
                int height = Integer.parseInt(heightText.trim());
                textLines = getLines(height, width, "line", distanceText);
            } catch(NumberFormatException e) {
                ExceptionHandler.handleException(e);
            }
        } else {
            textLines = new String[0];
        }
        return textLines;
    }

    /**
     * Default implementation for single-sided objects.
     */
    public String[] getMaskData(int position, int direction, int distance) {
        String distanceText = String.valueOf(distance);
        String widthText = m_data.getProperty("width." + distanceText);
        String heightText = m_data.getProperty("height." + distanceText);

        String[] textLines = null;
        if(widthText != null && heightText != null) {
            try {
                int width = Integer.parseInt(widthText.trim());  // width info not necessary at all?
                int height = Integer.parseInt(heightText.trim());
                textLines = getLines(height, width, "mask", distanceText);
            } catch(NumberFormatException e) {
                textLines = new String[2];
                textLines[0] = "Failed to convert single mask width '" + widthText + "'";
                textLines[1] = "             and single mask height '" + heightText + "'";
            }
        } else {
            textLines = new String[0];
        }

        return textLines;
    }

    private String[] getLines(int height, int width, String type, String distanceText) {
        String[] textLines = new String[height];
        for(int i=0; i<height; i++) {
            textLines[i] = m_data.getProperty(type + "." + distanceText + "." + String.valueOf(i+1));
            textLines[i] = textLines[i].substring(1, width);
        }
        return textLines;
    }
}
