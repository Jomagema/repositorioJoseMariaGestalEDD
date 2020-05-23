package com.jlaby.util;

/*
 * @(#)PropertyUtil.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.util.*;
import java.io.*;

/**
 * Utility class for property handling
 *
 * @author  Marcel Schoen
 * @version $Id: PropertyUtil.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 */
public class PropertyUtil {

    /**
     * Load Properties either from a file in the filesystem
     * or in the classpath.
     */
    public static Properties load(String resource) {
        Properties result = new Properties();
        try {
            // 1st try: Read it from file in filesystem
            File test = new File(resource);
            if(test.exists() && test.isFile()) {
                System.out.println("Load file resource: "+resource);
                result.load(new FileInputStream(test));
            } else {
                // 2nd try: Read it from file in classpath
                InputStream in = test.getClass().getResourceAsStream(resource);
                if(in != null) {
                    result.load(in);
                } else {
                    throw new IllegalArgumentException("Could not load "+resource);
                }
            }
        } catch(Exception e) {
            throw new IllegalArgumentException("Could not load "+resource);
        }
        return result;
    }
}
