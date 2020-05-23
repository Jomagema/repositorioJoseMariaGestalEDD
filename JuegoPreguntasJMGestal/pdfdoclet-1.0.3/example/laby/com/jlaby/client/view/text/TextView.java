package com.jlaby.client.view.text;

/*
 * @(#)TextView.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.config.*;
import java.io.*;

/**
 * The class which renders the "3D" view. Usage
 * of this class by other classes:
 *
 * 1. initialize once
 *
 * then, for every image update,
 *
 * 3. 1-n x addGraphics(...)
 * 4. image = renderView();
 *
 * @author  Marcel Schoen
 * @version $Id: TextView.java,v 1.1 2013/10/27 23:51:26 marcelschoen Exp $
 */
public class TextView {

    private static boolean ms_initialized = false;
    private static String FONTTAG = "<font color=";
    private static String FONTENDTAG = "</font>";
    private static String COLOR_BLACK = "\"#000000\">";
    private static String COLOR_WHITE = "\"#ffffff\">";
    private static String BORDERTABLE_START = "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><pre><font size=-1>";
    private static String BORDERTABLE_END = "<br></font></pre></td></tr></table>";

    private static int ms_numberOfLines = 0;
    private static int ms_numberOfColumns = 0;
    private static char ms_transparency = ':';
    private static char ms_backgroundColor = 'o';

    private StringBuffer[] m_lines = null;
    private StringBuffer[] m_colors = null;

    /**
     * Renders a string picture with <FONT COLOR> tags
     */
    public String[] renderView() {

        String[] result = null;
        String[] resultBuffer = null;

        result = new String[ms_numberOfLines];
        resultBuffer = new String[ms_numberOfLines];

        for(int y=0; y<ms_numberOfLines; y++) {
            resultBuffer[y] = new String(m_lines[y]);
        }


        for(int y=0; y<ms_numberOfLines; y++) {
            char currentColor = 'o';
            int x2 = 0, x3 = 0;
            result[y] = FONTTAG + COLOR_BLACK;
            for(int x=0; x<ms_numberOfColumns; x++) {
                char charColor = m_colors[y].charAt(x);
                if(charColor != currentColor) {
                    if(x == 0) {
                        result[y] = result[y] + FONTENDTAG + FONTTAG + TextGraphics.getColorValue(charColor) + ">";
                    } else {
                        result[y] = result[y] +
                            resultBuffer[y].substring(x2, x3) +
                            FONTENDTAG + FONTTAG + TextGraphics.getColorValue(charColor) + ">";
                    }
                    currentColor = charColor;
                    x2 = x;
                    x3 = x;
                }
                x3 ++;
            }
            result[y] = result[y] + resultBuffer[y].substring(x2, x3);
        }

        resultBuffer = new String[ms_numberOfLines+2];
        resultBuffer[0] = BORDERTABLE_START + FONTTAG + COLOR_WHITE;

        resultBuffer[resultBuffer.length-1] = BORDERTABLE_END;
        for(int i=0; i<ms_numberOfLines; i++) {
            resultBuffer[i+1] = result[i] + "<BR>";
        }

        return resultBuffer;
    }

    public TextView() {
        if(!ms_initialized) {
            initialize();
        }
        m_lines = new StringBuffer[ms_numberOfLines];
        m_colors = new StringBuffer[ms_numberOfLines];
        for(int i=0; i<ms_numberOfLines; i++) {
            m_lines[i]  = new StringBuffer();
            m_colors[i]  = new StringBuffer();
            m_lines[i].setLength(ms_numberOfColumns);
            m_colors[i].setLength(ms_numberOfColumns);

            for(int x=0; x<ms_numberOfColumns; x++) {
                m_lines[i].setCharAt(x, ' ');
                m_colors[i].setCharAt(x, ms_backgroundColor);
            }
        }
    }

    public static void initialize() {
        ms_numberOfLines = Configuration.getNumericProperty("graphics.textview.lines");
        ms_numberOfColumns = Configuration.getNumericProperty("graphics.textview.columns");
        String transparencyText = Configuration.getProperty("graphics.textview.transparency");
        ms_transparency = transparencyText.charAt(0);
        String bgColorText = Configuration.getProperty("graphics.textview.bgcolor");
        ms_backgroundColor = bgColorText.charAt(0);
        ms_initialized = true;
    }

    public void addGraphics(int line, int column, String[] graphics, String[] mask) {
        if(graphics != null && graphics.length > 0) {
            int screenY = line;
            for(int readY=0; readY < graphics.length; readY++) {
                if(screenY > -1 && screenY < ms_numberOfLines) {
                    String graphicsLine = graphics[readY];
                    String maskLine = mask[readY];
                    int screenX = column;
                    for(int readX = 0; readX < graphicsLine.length(); readX++) {
                        if(screenX > -1 && screenX < ms_numberOfColumns) {
                            char graphicsChar = graphicsLine.charAt(readX);
                            char colorChar = maskLine.charAt(readX);
                            addGraphicsCharacter(screenY, screenX, graphicsChar, colorChar);
                        }
                        screenX++;
                    }
                }
                screenY++;
            }
        }
    }

    public void addGraphics(int line, int column, String[] graphics, String color) {
        if(graphics != null && graphics.length > 0) {
            int screenY = line;
            char colorChar = ' ';
            String colorCharText = TextGraphics.getColorCharacter(color);

            if(colorCharText == null) {
                colorChar = 'G';
            } else {
                colorChar = colorCharText.charAt(0);
            }
            for(int readY=0; readY < graphics.length; readY++) {
                if(screenY > -1 && screenY < ms_numberOfLines) {
                    String graphicsLine = graphics[readY];
                    int screenX = column;
                    for(int readX = 0; readX < graphicsLine.length(); readX++) {
                        if(screenX > -1 && screenX < ms_numberOfColumns) {
                            char graphicsChar = graphicsLine.charAt(readX);
                            addGraphicsCharacter(screenY, screenX, graphicsChar, colorChar);
                        }
                        screenX++;
                    }
                }
                screenY++;
            }
        }
    }

    private void addGraphicsCharacter(int line, int column, char character, char color) {
        if(character != ms_transparency) {
            m_lines[line].setCharAt(column, character);
            m_colors[line].setCharAt(column, color);
        }
    }
}
