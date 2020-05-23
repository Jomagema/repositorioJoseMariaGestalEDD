package com.jlaby.config;

/*
 * @(#)Configuration.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 */
import java.util.Properties;

import com.jlaby.exception.*;
import com.jlaby.util.*;

/**
 * Helper class for accessing configuration values
 * set in a specific properties file in the classpath
 * or from the local filesystem.
 *
 * @version $Id: Configuration.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 * @author  Marcel Schoen, TARSEC AG
 */
public class Configuration {

    private static Properties ms_configValues = new Properties();

    public static void load(String resource) {
        ms_configValues = PropertyUtil.load(resource);
    }

    public static String getProperty(String property) {
        String value = ms_configValues.getProperty(property);
        if(value == null) {
            throw new IllegalArgumentException("Missing config property: " + property);
        }
        return value;
    }

    public static String getProperty(String property, String defaultValue) {
        return ms_configValues.getProperty(property, defaultValue);
    }

    public static int getNumericProperty(String property) {
        String text = ms_configValues.getProperty(property);
        if(text == null) {
            throw new IllegalArgumentException("Missing numeric config property: " + property);
        }
        int value = 0;
        try {
            value = Integer.parseInt(text.trim());
        } catch(NumberFormatException e) {
            ExceptionHandler.handleException(e);
            value = -99999999;
        }
        return value;
    }

    public static int getNumericProperty(String property, int defaultValue) {
        int value = getNumericProperty(property);
        if(value == 99999999) {
            value = defaultValue;
        }
        return value;
    }
}
