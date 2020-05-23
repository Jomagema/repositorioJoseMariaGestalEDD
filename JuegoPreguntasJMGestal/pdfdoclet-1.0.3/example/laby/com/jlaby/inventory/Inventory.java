package com.jlaby.inventory;

/*
 * @(#)Inventory.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import com.jlaby.util.*;
import com.jlaby.config.*;

/**
 * This class handles the inventory.
 *
 * @author  Marcel Schoen
 * @version $Id: Inventory.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class Inventory {

    private static boolean initialized = false;
    private static String[] lines = null;

    public static String[] display(PrintWriter out) {
        try {
            // Now display the inventory
            return lines;
        } catch(Exception e) {
            e.printStackTrace(out);
        }
        return null;
    }

    public Inventory() {
        String templateFile = Configuration.getProperty("inventory.html.template");
        try {
            // 1st try: Read it from file in filesystem
            File test = new File(templateFile);
            if(test.exists() && test.isFile()) {
                lines = FileUtil.readTextFile(test.getAbsolutePath());
                initialized = true;
            } else {
                // 2nd try: Read it from file in classpath
                InputStream in = getClass().getResourceAsStream(templateFile);
                if(in != null) {
                    lines = FileUtil.readTextFileStream(new InputStreamReader(in));
                    initialized = true;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
