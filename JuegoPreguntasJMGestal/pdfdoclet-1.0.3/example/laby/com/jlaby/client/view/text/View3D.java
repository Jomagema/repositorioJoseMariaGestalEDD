package com.jlaby.client.view.text;

/*
 * @(#)View3D.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.client.*;
import com.jlaby.config.*;
import com.jlaby.log.*;
import com.jlaby.util.*;
import com.jlaby.world.*;
import com.jlaby.view.*;

import java.util.*;
import java.io.*;

/**
 * The class which renders the "3D" view.
 *
 * @author  Marcel Schoen
 * @version $Id: View3D.java,v 1.1 2013/10/27 23:51:26 marcelschoen Exp $
 */
public class View3D {

    private static int[] m_order = { 9999, 1, 5,   2,  4,  3,
                                           6, 10,  7,  9,  8,
                                          11, 15, 12, 14, 13,
                                          16, 20, 17, 19, 18,
                                              21, 23, 22,
                                              24,     25 };

    private static int[] m_distance = { 9999, 5,  5,  5,  5,  5,
                                 4,  4,  4,  4,  4,
                                 3,  3,  3,  3,  3,
                                 2,  2,  2,  2,  2,
                                 1,  1,  1,
                                 0,      0 };

    private static int[] ms_map = {
         1, 2, 3, 4, 5,
         6, 7, 8, 9,10,
         11,12,13,14,15,
         16,17,18,19,20,
         -1,21,22,23,-1,
         -1,24,-1,25,-1};

    private static int[] m_frontalOffset = new int[6];
    private static int[] m_xpos = new int[26];
    private static int[] m_ypos = new int[26];
    private static Properties m_positions = null;

    private static boolean ms_initialized = false;


    /**
     * Renders a 3D string picture.
     */
    public static String[] renderView(GameCharacterInfo characterInfo) {

        String[] result = null;
        TextView textView = new TextView();
        ViewObject[] objects = characterInfo.getViewObjects();

        // if no objects are visible, return 0 lines
        if(objects == null) {
            objects = new ViewObject[0];
        }

        if(!ms_initialized) {
            initialize();
        }

        for(int no=1; no<26; no++) {

            int position = m_order[no];
            int distance = m_distance[no];

            for(int obj=0; obj<objects.length; obj++) {

                int objPos = getFieldNumber(characterInfo.getDirection(), objects[obj].getPosition());

                if(objPos == position) {
                    int xScreenPos = m_xpos[objPos];
                    int yScreenPos = m_ypos[objPos];
                    TextGraphics graphics = TextGraphicsFactory.load(objects[obj].getObjectType());
                    String[] lines = graphics.getGraphics(position,
                                                          objects[obj].getDirection(),
                                                          distance);
                    if(graphics.getColorType() == TextGraphics.MULTI_COLOR) {
                        String[] mask = graphics.getMask(position,
                                                         objects[obj].getDirection(),
                                                         distance);
                        textView.addGraphics(yScreenPos, xScreenPos, lines, mask);
                    } else {
                        textView.addGraphics(yScreenPos, xScreenPos, lines, graphics.getColor());
                    }
                }
            }
        }

        result = textView.renderView();

        return result;
    }

    /**
     * This method translates the relative position of an object
     * into the number of the field in the viewing area. If the
     * object is not within the visible area, a value lower than 1
     * will be returned.
     */
    private static int getFieldNumber(int direction, Position position) {

        int fieldNum = 0;
        int startX = 0;
        int startY = 0;
        int fieldX = 1;
        int fieldY = 1;
        int rowX = 1;
        int rowY = 1;
        switch(direction) {
        case IWorld.NORTH:
            startY = - 5;
            startX = - 2;
            fieldX = 1;
            fieldY = 0;
            rowX = 0;
            rowY = 1;
            break;
        case IWorld.SOUTH:
            startY = 5;
            startX = 2;
            fieldX = -1;
            fieldY = 0;
            rowX = 0;
            rowY = -1;
            break;
        case IWorld.EAST:
            startY = - 2;
            startX = 5;
            fieldX = 0;
            fieldY = 1;
            rowX = -1;
            rowY = 0;
            break;
        case IWorld.WEST:
            startY = 2;
            startX = - 5;
            fieldX = 0;
            fieldY = -1;
            rowX = 1;
            rowY = 0;
            break;
        }

        int worldY = startY, worldX = startX, fieldCounter = 0;
        for(int fieldRow=0; fieldRow<6 && fieldNum<1; fieldRow++) {
            for(int fieldColumn=0; fieldColumn<5 && fieldNum<1; fieldColumn++) {
                if(position.getX() == worldX && position.getY() == worldY) {
                    fieldNum = ms_map[fieldCounter];
                }
                fieldCounter ++;
                worldX += fieldX;
                worldY += fieldY;
            }
            startY += rowY;
            startX += rowX;
            worldY = startY;
            worldX = startX;
        }

        return fieldNum;
    }

    private static void initialize() {
        String numberText, propName;

        TextGraphics.initialize();
        TextGraphicsFactory.initialize("/com/jlaby/client/view/text/data/index.properties");

        for(int i=1; i<6; i++) {
            propName = "frontal.off." + String.valueOf(i);
            m_frontalOffset[i] = Configuration.getNumericProperty(propName);
        }

        for(int i=1; i<26; i++) {

            propName = "xpos." + String.valueOf(i);
            m_xpos[i] = Configuration.getNumericProperty(propName);

            propName = "ypos." + String.valueOf(i);
            m_ypos[i] = Configuration.getNumericProperty(propName);

        }
        ms_initialized = true;
    }
}
