package com.jlaby.client.view.text;

/*
 * @(#)WallGraphics.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */

/**
 * Helper class which makes it easier to implement
 * objects for the laby. It provides some methods
 * to load graphics from resource files in the classpath etc.
 *
 * @author  Marcel Schoen
 * @version $Id: WallGraphics.java,v 1.1 2013/10/27 23:51:26 marcelschoen Exp $
 */
public class WallGraphics extends TextGraphics {

    /**
     * Special implementation for wall graphics. The difference
     * to normal multi-sided graphics is that the direction of
     * the "object" is always given, but it depends on the
     * position in the view rectangle.
     */
    public String[] getGraphicsData(int position, int direction, int distance) {
        String postext = "." + String.valueOf(position);

        String widthText = m_data.getProperty("width" + postext);
        String heightText = m_data.getProperty("height" + postext);
        String[] textLines = null;

        try {
            int width = Integer.parseInt(widthText.trim());  // width info not necessary at all?
            int height = Integer.parseInt(heightText.trim());

            textLines = new String[height];
            for(int i=0; i<height; i++) {
                textLines[i] = m_data.getProperty("line" + postext + "." + String.valueOf(i+1));
                textLines[i] = textLines[i].substring(1, width+1);
            }
        } catch(NumberFormatException e) {
            textLines = new String[2];
            textLines[0] = "Failed to convert wall width '" + widthText + "'";
            textLines[1] = "             and wall height '" + heightText + "'";
        }
        return textLines;
    }

    /**
     * Special implementation for wall graphics. The difference
     * to normal multi-sided graphics is that the direction of
     * the "object" is always given, but it depends on the
     * position in the view rectangle.
     */
    public String[] getMaskData(int position, int direction, int distance) {
        String[] textLines = new String[0];
        return textLines;
    }
}
